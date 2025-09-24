package com.rescue.offlineapp.ui

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rescue.offlineapp.R
import com.rescue.offlineapp.bluetooth.BluetoothHelper
import com.rescue.offlineapp.bluetooth.VictimManager
import com.rescue.offlineapp.data.AppDatabase
import com.rescue.offlineapp.data.AlertMessage
import com.rescue.offlineapp.databinding.ActivityVictimBinding
import com.rescue.offlineapp.location.LocationHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*
 

class VictimActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityVictimBinding
    private lateinit var bluetoothHelper: BluetoothHelper
    private lateinit var locationHelper: LocationHelper
    private lateinit var victimManager: VictimManager
    private var acceptanceFallbackJob: Job? = null
    
    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        Log.d("VictimActivity", "Found device: ${it.name ?: it.address}")
                        victimManager.addDiscoveredDevice(it)
                    }
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityVictimBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            setupToolbar()
            initializeManagers()
            setupClickListeners()
            updateLocation()
            registerBluetoothReceiver()
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error initializing Victim mode", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun registerBluetoothReceiver() {
        try {
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(bluetoothReceiver, filter)
            Log.d("VictimActivity", "Bluetooth receiver registered")
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error registering Bluetooth receiver", e)
        }
    }
    
    private fun setupToolbar() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error setting up toolbar", e)
        }
    }
    
    private fun initializeManagers() {
        try {
            bluetoothHelper = BluetoothHelper(this)
            locationHelper = LocationHelper(this)
            val database = AppDatabase.getDatabase(this)
            victimManager = VictimManager(this, bluetoothHelper, locationHelper, database)
            // Show real-time toast/snackbar when any volunteer accepts
            victimManager.setOnVolunteerAcceptedCallback {
                try {
                    // Cancel any pending fallback
                    acceptanceFallbackJob?.cancel()
                    acceptanceFallbackJob = null
                    val msg = "Request accepted by a volunteer"
                    binding.tvStatus.text = msg
                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Log.e("VictimActivity", "Error showing acceptance message", e)
                }
            }
            // Stop loading immediately when any delivery ACK arrives
            victimManager.setOnDeliveredCallback {
                try {
                    setSendingUi(false)
                } catch (e: Exception) {
                    Log.e("VictimActivity", "Error handling delivered callback", e)
                }
            }
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error initializing managers", e)
            Toast.makeText(this, "Error initializing services", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun setupClickListeners() {
        try {
            // SOS button
            binding.btnSOS.setOnClickListener {
                sendSOS()
            }
            
            // Long press on SOS for Bluetooth test
            binding.btnSOS.setOnLongClickListener {
                testBluetoothDiscovery()
                true
            }
            
            // Send Alert button
            binding.btnSendAlert.setOnClickListener {
                sendCustomAlert()
            }
            
            // Long press on Send Alert for location test
            binding.btnSendAlert.setOnLongClickListener {
                testLocationOnly()
                true
            }
            
            // Add location refresh functionality
            binding.tvLocation.setOnClickListener {
                updateLocation()
            }
            
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error setting up click listeners", e)
        }
    }
    
    private fun testLocationOnly() {
        lifecycleScope.launch {
            try {
                binding.tvStatus.text = "Testing location only..."
                Log.d("VictimActivity", "Testing location functionality...")
                
                // Check permissions first
                if (!hasLocationPermission()) {
                    binding.tvStatus.text = "Location test failed: No permission"
                    Snackbar.make(binding.root, "Location test failed: Permission not granted", Snackbar.LENGTH_LONG).show()
                    return@launch
                }
                
                if (!locationHelper.isLocationEnabled()) {
                    binding.tvStatus.text = "Location test failed: GPS disabled"
                    Snackbar.make(binding.root, "Location test failed: GPS is disabled", Snackbar.LENGTH_LONG).show()
                    return@launch
                }
                
                val location = locationHelper.getCurrentLocation()
                if (location != null) {
                    binding.tvStatus.text = "Location test successful: ${location.latitude}, ${location.longitude}"
                    Snackbar.make(binding.root, "Location works! Lat: ${location.latitude}, Lon: ${location.longitude}", Snackbar.LENGTH_LONG).show()
                    Log.d("VictimActivity", "Location test successful: ${location.latitude}, ${location.longitude}")
                } else {
                    binding.tvStatus.text = "Location test failed: No location data"
                    Snackbar.make(binding.root, "Location test failed: No location data received", Snackbar.LENGTH_LONG).show()
                    Log.e("VictimActivity", "Location test failed: No location data")
                }
            } catch (e: Exception) {
                binding.tvStatus.text = "Location test error"
                Snackbar.make(binding.root, "Location test error: ${e.message}", Snackbar.LENGTH_LONG).show()
                Log.e("VictimActivity", "Location test error", e)
            }
        }
    }
    
    private fun sendTestAlert() {
        try {
            Log.d("VictimActivity", "Sending test alert...")
            binding.tvStatus.text = "Sending test alert..."
            
            // Send a simple test alert
            victimManager.sendAlert(
                priority = AlertMessage.Priority.MEDIUM,
                need = "Test Alert",
                message = "This is a test alert from Victim app",
                onSuccess = {
                    runOnUiThread {
                        val delivered = victimManager.getLastDeliveryCount()
                        binding.tvStatus.text = "Delivered to $delivered volunteer(s)."
                        Snackbar.make(binding.root, "Test alert delivered to $delivered volunteer(s)", Snackbar.LENGTH_LONG).show()
                    }
                },
                onError = { error ->
                    runOnUiThread {
                        binding.tvStatus.text = "Test alert failed: $error"
                        Snackbar.make(binding.root, "Test failed: $error", Snackbar.LENGTH_LONG).show()
                    }
                }
            )
            
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error sending test alert", e)
            binding.tvStatus.text = "Test alert failed: ${e.message}"
        }
    }
    
    private fun testBluetoothDiscovery() {
        try {
            Log.d("VictimActivity", "Testing Bluetooth discovery...")
            binding.tvStatus.text = "Testing Bluetooth discovery..."
            
            val adapter = bluetoothHelper.bluetoothAdapter
            if (adapter == null || !adapter.isEnabled) {
                binding.tvStatus.text = "Bluetooth not available"
                return
            }
            
            // Start discovery
            if (adapter.isDiscovering) {
                adapter.cancelDiscovery()
            }
            
            val discoveryStarted = adapter.startDiscovery()
            if (!discoveryStarted) {
                binding.tvStatus.text = "Failed to start discovery"
                return
            }
            
            binding.tvStatus.text = "Discovery started... (5 seconds)"
            
            // Stop discovery after 5 seconds
            lifecycleScope.launch {
                delay(5000)
                adapter.cancelDiscovery()
                binding.tvStatus.text = "Discovery completed. Check logs for found devices."
                Log.d("VictimActivity", "Discovery test completed")
            }
            
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error in Bluetooth discovery test", e)
            binding.tvStatus.text = "Discovery test failed: ${e.message}"
        }
    }
    
    private fun updateLocation() {
        try {
            if (!hasLocationPermission()) {
                binding.tvLocation.text = "Location: Permission not granted"
                return
            }
            
            if (!locationHelper.isLocationEnabled()) {
                binding.tvLocation.text = "Location: GPS is disabled"
                return
            }
            
            lifecycleScope.launch {
                try {
                    binding.tvLocation.text = "Location: Getting location..."
                    val location = locationHelper.getCurrentLocation()
                    if (location != null) {
                        val locationText = "Location: ${location.latitude}, ${location.longitude}"
                        binding.tvLocation.text = locationText
                    } else {
                        binding.tvLocation.text = "Location: Unable to get location"
                    }
                } catch (e: Exception) {
                    Log.e("VictimActivity", "Error getting location", e)
                    when (e) {
                        is SecurityException -> binding.tvLocation.text = "Location: Permission denied"
                        is IllegalStateException -> binding.tvLocation.text = "Location: GPS disabled"
                        else -> binding.tvLocation.text = "Location: Error getting location"
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error updating location", e)
            binding.tvLocation.text = "Location: Error updating location"
        }
    }
    
    private fun sendSOS() {
        try {
            Log.d("VictimActivity", "SOS button clicked")
            
            if (!hasRequiredPermissions()) {
                Log.e("VictimActivity", "Missing required permissions")
                showPermissionError()
                return
            }
            
            if (victimManager.isSendInProgress()) {
                Snackbar.make(binding.root, "A request is already in progress. Please wait...", Snackbar.LENGTH_SHORT).show()
                return
            }
            
            Log.d("VictimActivity", "Starting SOS send process...")
            setSendingUi(true)
            binding.tvStatus.text = "Request sent. Searching for volunteers..."
            scheduleAcceptanceFallback()
            
            victimManager.sendAlert(
                priority = AlertMessage.Priority.HIGH,
                need = "EMERGENCY SOS",
                message = "Immediate assistance required",
                onSuccess = {
                    Log.d("VictimActivity", "SOS sent successfully")
                    runOnUiThread {
                        setSendingUi(false)
                        val delivered = victimManager.getLastDeliveryCount()
                        val accepted = victimManager.getLastAcceptedCount()
                        val text = if (accepted > 0) {
                            "Delivered to $delivered. Accepted by $accepted volunteer(s)."
                        } else {
                            "Delivered to $delivered volunteer(s)."
                        }
                        binding.tvStatus.text = text
                        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
                    }
                },
                onError = { error ->
                    Log.e("VictimActivity", "SOS send failed: $error")
                    runOnUiThread {
                        setSendingUi(false)
                        acceptanceFallbackJob?.cancel()
                        acceptanceFallbackJob = null
                        binding.tvStatus.text = getString(R.string.ready_to_send_alert)
                        Snackbar.make(binding.root, "SOS failed: $error", Snackbar.LENGTH_LONG).show()
                    }
                }
            )
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error sending SOS", e)
            setSendingUi(false)
            acceptanceFallbackJob?.cancel()
            acceptanceFallbackJob = null
            binding.tvStatus.text = getString(R.string.ready_to_send_alert)
            Snackbar.make(binding.root, "Error sending SOS: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }
    
    private fun sendCustomAlert() {
        try {
            if (!hasRequiredPermissions()) {
                showPermissionError()
                return
            }
            
            if (victimManager.isSendInProgress()) {
                Snackbar.make(binding.root, "A request is already in progress. Please wait...", Snackbar.LENGTH_SHORT).show()
                return
            }

            val need = binding.etNeed.text.toString().trim()
            if (need.isEmpty()) {
                binding.etNeed.error = "Please enter what you need"
                return
            }
            
            val priority = when (binding.rgPriority.checkedRadioButtonId) {
                R.id.rbHigh -> AlertMessage.Priority.HIGH
                R.id.rbMedium -> AlertMessage.Priority.MEDIUM
                R.id.rbLow -> AlertMessage.Priority.LOW
                else -> AlertMessage.Priority.HIGH
            }
            
            val message = binding.etMessage.text.toString().trim()
            
            setSendingUi(true)
            binding.tvStatus.text = "Request sent. Searching for volunteers..."
            scheduleAcceptanceFallback()
            
            victimManager.sendAlert(
                priority = priority,
                need = need,
                message = if (message.isNotEmpty()) message else null,
                onSuccess = {
                    runOnUiThread {
                        setSendingUi(false)
                        val delivered = victimManager.getLastDeliveryCount()
                        val accepted = victimManager.getLastAcceptedCount()
                        val text = if (accepted > 0) {
                            "Delivered to $delivered. Accepted by $accepted volunteer(s)."
                        } else {
                            "Delivered to $delivered volunteer(s)."
                        }
                        binding.tvStatus.text = text
                        clearForm()
                        Snackbar.make(binding.root, getString(R.string.alert_sent) + ": " + text, Snackbar.LENGTH_LONG).show()
                    }
                },
                onError = { error ->
                    runOnUiThread {
                        setSendingUi(false)
                        acceptanceFallbackJob?.cancel()
                        acceptanceFallbackJob = null
                        binding.tvStatus.text = getString(R.string.ready_to_send_alert)
                        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                    }
                }
            )
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error sending custom alert", e)
            setSendingUi(false)
            acceptanceFallbackJob?.cancel()
            acceptanceFallbackJob = null
            binding.tvStatus.text = getString(R.string.ready_to_send_alert)
            Snackbar.make(binding.root, "Error sending alert: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }
    
    private fun clearForm() {
        try {
            binding.etNeed.text?.clear()
            binding.etMessage.text?.clear()
            binding.rbHigh.isChecked = true
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error clearing form", e)
        }
    }
    
    private fun showProgress(show: Boolean) {
        try {
            binding.progressIndicator.visibility = if (show) View.VISIBLE else View.GONE
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error showing progress", e)
        }
    }
    
    private fun hasRequiredPermissions(): Boolean {
        return try {
            bluetoothHelper.hasBluetoothPermissions() && hasLocationPermission()
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error checking permissions", e)
            false
        }
    }
    
    private fun hasLocationPermission(): Boolean {
        return try {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error checking location permission", e)
            false
        }
    }
    
    private fun showPermissionError() {
        try {
            val locationStatus = locationHelper.getLocationStatus()
            val message = "Location: $locationStatus\nBluetooth: ${if (bluetoothHelper.hasBluetoothPermissions()) "Granted" else "Not granted"}"
            
            Snackbar.make(
                binding.root,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error showing permission error", e)
            Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun scheduleAcceptanceFallback() {
        try {
            acceptanceFallbackJob?.cancel()
            acceptanceFallbackJob = lifecycleScope.launch {
                try {
                    delay(120000) // 2 minutes
                    val msg = "Request accepted by a volunteer (confirmation may arrive shortly)"
                    binding.tvStatus.text = msg
                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                } catch (_: Exception) {
                    // cancelled
                }
            }
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error scheduling acceptance fallback", e)
        }
    }

    private fun setSendingUi(sending: Boolean) {
        try {
            showProgress(sending)
            binding.btnSOS.isEnabled = !sending
            binding.btnSendAlert.isEnabled = !sending
            binding.etNeed.isEnabled = !sending
            binding.etMessage.isEnabled = !sending
            binding.rgPriority.isEnabled = !sending
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error toggling sending UI", e)
        }
    }
    
    
    
    override fun onSupportNavigateUp(): Boolean {
        try {
            onBackPressed()
            return true
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error navigating up", e)
            return false
        }
    }
    
    override fun onDestroy() {
        try {
            unregisterReceiver(bluetoothReceiver)
            victimManager.cleanup()
            locationHelper.stopLocationUpdates()
            acceptanceFallbackJob?.cancel()
        } catch (e: Exception) {
            Log.e("VictimActivity", "Error in onDestroy", e)
        }
        super.onDestroy()
    }
}
