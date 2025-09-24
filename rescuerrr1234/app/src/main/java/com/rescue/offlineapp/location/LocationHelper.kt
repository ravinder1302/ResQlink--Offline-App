package com.rescue.offlineapp.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationHelper(private val context: Context) {
    
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    
    private val locationRequest: LocationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000) // 5 seconds
            .setMinUpdateDistanceMeters(5f)
            .setMaxUpdates(1) // Only need one location
            .build()
    }
    
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                Log.d("LocationHelper", "Location updated: ${location.latitude}, ${location.longitude}")
            }
        }
    }
    
    suspend fun getCurrentLocation(): Location? {
        return try {
            withTimeout(30000) { // 30 second timeout
                suspendCancellableCoroutine { continuation ->
                    if (!hasLocationPermission()) {
                        Log.e("LocationHelper", "Location permission not granted")
                        continuation.resumeWithException(SecurityException("Location permission not granted"))
                        return@suspendCancellableCoroutine
                    }
                    
                    if (!isLocationEnabled()) {
                        Log.e("LocationHelper", "Location services are disabled")
                        continuation.resumeWithException(IllegalStateException("Location services are disabled. Please enable GPS."))
                        return@suspendCancellableCoroutine
                    }
                    
                    Log.d("LocationHelper", "Requesting current location...")
                    
                    // Try to get last known location first
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                Log.d("LocationHelper", "Got last known location: ${location.latitude}, ${location.longitude}")
                                continuation.resume(location)
                            } else {
                                Log.d("LocationHelper", "No last known location, requesting fresh location...")
                                // If no last known location, request fresh location
                                requestFreshLocation(continuation)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("LocationHelper", "Error getting last location", exception)
                            // Try requesting fresh location as fallback
                            requestFreshLocation(continuation)
                        }
                }
            }
        } catch (e: Exception) {
            Log.e("LocationHelper", "Error getting current location", e)
            null
        }
    }
    
    private fun requestFreshLocation(continuation: kotlinx.coroutines.CancellableContinuation<Location?>) {
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    Log.d("LocationHelper", "Got fresh location: ${location.latitude}, ${location.longitude}")
                    continuation.resume(location)
                } else {
                    Log.e("LocationHelper", "No location received from fresh request")
                    continuation.resume(null)
                }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { exception ->
            Log.e("LocationHelper", "Error requesting location updates", exception)
            fusedLocationClient.removeLocationUpdates(callback)
            continuation.resumeWithException(exception)
        }
    }
    
    fun startLocationUpdates(): Flow<Location> = callbackFlow {
        if (!hasLocationPermission()) {
            Log.e("LocationHelper", "Location permission not granted for updates")
            close(SecurityException("Location permission not granted"))
            return@callbackFlow
        }
        
        if (!isLocationEnabled()) {
            Log.e("LocationHelper", "Location services disabled for updates")
            close(IllegalStateException("Location services are disabled"))
            return@callbackFlow
        }
        
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("LocationHelper", "Location update: ${location.latitude}, ${location.longitude}")
                    trySend(location)
                }
            }
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { exception ->
            Log.e("LocationHelper", "Error starting location updates", exception)
            close(exception)
        }
        
        awaitClose {
            Log.d("LocationHelper", "Stopping location updates")
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }
    
    fun stopLocationUpdates() {
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            Log.d("LocationHelper", "Location updates stopped")
        } catch (e: Exception) {
            Log.e("LocationHelper", "Error stopping location updates", e)
        }
    }
    
    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
               locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    
    fun getLocationStatus(): String {
        return when {
            !hasLocationPermission() -> "Location permission not granted"
            !isLocationEnabled() -> "Location services are disabled"
            else -> "Location services available"
        }
    }
}
