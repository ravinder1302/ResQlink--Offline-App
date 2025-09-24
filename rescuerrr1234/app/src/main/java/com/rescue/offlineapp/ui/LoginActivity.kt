package com.rescue.offlineapp.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rescue.offlineapp.R
import com.rescue.offlineapp.bluetooth.BluetoothHelper
import com.rescue.offlineapp.data.UserRole
import com.rescue.offlineapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var bluetoothHelper: BluetoothHelper
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        try {
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                checkBluetoothAndLocation()
            } else {
                showPermissionDeniedDialog()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error handling permissions", e)
            showPermissionDeniedDialog()
        }
    }
    
    private val enableBluetoothLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            if (result.resultCode == RESULT_OK) {
                checkLocationPermission()
            } else {
                showBluetoothRequiredDialog()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error handling Bluetooth result", e)
            showBluetoothRequiredDialog()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            bluetoothHelper = BluetoothHelper(this)
            
            setupClickListeners()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error initializing app", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun setupClickListeners() {
        try {
            binding.cardVictim.setOnClickListener {
                selectRole(UserRole.VICTIM)
            }
            
            binding.cardVolunteer.setOnClickListener {
                selectRole(UserRole.VOLUNTEER)
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error setting up click listeners", e)
        }
    }
    
    private fun selectRole(role: UserRole) {
        try {
            // Store role in SharedPreferences
            getSharedPreferences("rescue_prefs", MODE_PRIVATE)
                .edit()
                .putString("user_role", role.name)
                .apply()
            
            // Check permissions before proceeding
            checkPermissions()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error selecting role", e)
            Toast.makeText(this, "Error selecting role", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun checkPermissions() {
        try {
            if (bluetoothHelper.hasBluetoothPermissions() && 
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                checkBluetoothAndLocation()
            } else {
                requestPermissions()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error checking permissions", e)
            requestPermissions()
        }
    }
    
    private fun requestPermissions() {
        try {
            val permissions = bluetoothHelper.getRequiredPermissions() + 
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            
            requestPermissionLauncher.launch(permissions)
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error requesting permissions", e)
            showPermissionDeniedDialog()
        }
    }
    
    private fun checkBluetoothAndLocation() {
        try {
            if (!bluetoothHelper.isBluetoothSupported()) {
                showBluetoothNotSupportedDialog()
                return
            }
            
            if (!bluetoothHelper.isBluetoothEnabled()) {
                showEnableBluetoothDialog()
                return
            }
            
            // All good, proceed to appropriate activity
            proceedToMainActivity()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error checking Bluetooth and location", e)
            showBluetoothNotSupportedDialog()
        }
    }
    
    private fun checkLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                proceedToMainActivity()
            } else {
                showLocationRequiredDialog()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error checking location permission", e)
            showLocationRequiredDialog()
        }
    }
    
    private fun proceedToMainActivity() {
        try {
            val role = getSharedPreferences("rescue_prefs", MODE_PRIVATE)
                .getString("user_role", UserRole.VICTIM.name)
            
            val intent = when (UserRole.valueOf(role!!)) {
                UserRole.VICTIM -> Intent(this, VictimActivity::class.java)
                UserRole.VOLUNTEER -> Intent(this, VolunteerActivity::class.java)
            }
            
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error proceeding to main activity", e)
            Toast.makeText(this, "Error starting app", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showEnableBluetoothDialog() {
        try {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.enable_bluetooth))
                .setMessage(getString(R.string.bluetooth_required_message))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    try {
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        enableBluetoothLauncher.launch(enableBtIntent)
                    } catch (e: Exception) {
                        Log.e("LoginActivity", "Error launching Bluetooth intent", e)
                        showBluetoothRequiredDialog()
                    }
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing Bluetooth dialog", e)
            showBluetoothRequiredDialog()
        }
    }
    
    private fun showBluetoothNotSupportedDialog() {
        try {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage("This device does not support Bluetooth")
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing Bluetooth not supported dialog", e)
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showLocationRequiredDialog() {
        try {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.enable_location))
                .setMessage(getString(R.string.location_required_message))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("LoginActivity", "Error launching settings", e)
                        Toast.makeText(this, "Please enable location in settings", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing location dialog", e)
            Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showPermissionDeniedDialog() {
        try {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.permission_required))
                .setMessage(getString(R.string.bluetooth_location_permission_message))
                .setPositiveButton(getString(R.string.grant_permissions)) { _, _ ->
                    requestPermissions()
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing permission denied dialog", e)
            Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showBluetoothRequiredDialog() {
        try {
            Toast.makeText(this, getString(R.string.bluetooth_required_message), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing Bluetooth required dialog", e)
        }
    }
}
