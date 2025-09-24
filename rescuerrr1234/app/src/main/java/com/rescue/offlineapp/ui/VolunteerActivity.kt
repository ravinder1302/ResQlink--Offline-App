package com.rescue.offlineapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rescue.offlineapp.R
import com.rescue.offlineapp.bluetooth.BluetoothHelper
import com.rescue.offlineapp.bluetooth.VolunteerManager
import com.rescue.offlineapp.data.AlertEntity
import com.rescue.offlineapp.data.AlertMessage
import com.rescue.offlineapp.data.AppDatabase
import com.rescue.offlineapp.databinding.ActivityVolunteerBinding
import com.rescue.offlineapp.ui.adapter.AlertAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class VolunteerActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityVolunteerBinding
    private lateinit var bluetoothHelper: BluetoothHelper
    private lateinit var volunteerManager: VolunteerManager
    private lateinit var database: AppDatabase
    private lateinit var alertAdapter: AlertAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityVolunteerBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            setupToolbar()
            initializeManagers()
            setupRecyclerView()
            setupClickListeners()
            observeAlerts()
            startListening()
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error in onCreate", e)
            Snackbar.make(findViewById(android.R.id.content), "Error initializing Volunteer mode", Snackbar.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun setupToolbar() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error setting up toolbar", e)
        }
    }
    
    private fun initializeManagers() {
        try {
            bluetoothHelper = BluetoothHelper(this)
            database = AppDatabase.getDatabase(this)
            volunteerManager = VolunteerManager(this, bluetoothHelper, database)
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error initializing managers", e)
            Snackbar.make(binding.root, "Error initializing services", Snackbar.LENGTH_LONG).show()
        }
    }
    
    private fun setupRecyclerView() {
        try {
            alertAdapter = AlertAdapter { alert ->
                // Accept: mark as read, send ACK_ACCEPTED back
                try {
                    lifecycleScope.launch {
                        database.alertDao().markAsRead(alert.id)
                    }
                    val accepted = volunteerManager.acceptAlert(alert.victimId, alert.timestamp)
                    Snackbar.make(
                        binding.root,
                        "Accepted",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.e("VolunteerActivity", "Error accepting alert", e)
                }
            }
            
            binding.rvAlerts.apply {
                layoutManager = LinearLayoutManager(this@VolunteerActivity)
                adapter = alertAdapter
            }
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error setting up RecyclerView", e)
        }
    }
    
    private fun setupClickListeners() {
        try {
            binding.switchListening.setOnCheckedChangeListener { _, isChecked ->
                try {
                    if (isChecked) {
                        startListening()
                    } else {
                        stopListening()
                    }
                } catch (e: Exception) {
                    Log.e("VolunteerActivity", "Error handling switch change", e)
                }
            }
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error setting up click listeners", e)
        }
    }
    
    private fun observeAlerts() {
        try {
            lifecycleScope.launch {
                try {
                    database.alertDao().getAllAlerts().collectLatest { alerts ->
                        updateAlertsList(alerts)
                    }
                } catch (e: Exception) {
                    Log.e("VolunteerActivity", "Error observing alerts", e)
                }
            }
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error setting up alert observation", e)
        }
    }
    
    private fun updateAlertsList(alerts: List<AlertEntity>) {
        try {
            alertAdapter.submitList(alerts)
            
            // Update UI based on alerts
            if (alerts.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.rvAlerts.visibility = View.GONE
                binding.tvAlertCount.text = "0"
            } else {
                binding.emptyState.visibility = View.GONE
                binding.rvAlerts.visibility = View.VISIBLE
                binding.tvAlertCount.text = alerts.size.toString()
            }
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error updating alerts list", e)
        }
    }
    
    private fun startListening() {
        try {
            volunteerManager.startListening { alertMessage ->
                // Handle new alert received
                try {
                    runOnUiThread {
                        Snackbar.make(
                            binding.root,
                            "New alert received: ${alertMessage.need}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e("VolunteerActivity", "Error handling new alert", e)
                }
            }
            
            updateStatus()
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error starting listening", e)
            Snackbar.make(binding.root, "Error starting listening", Snackbar.LENGTH_LONG).show()
        }
    }
    
    private fun stopListening() {
        try {
            volunteerManager.stopListening()
            updateStatus()
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error stopping listening", e)
        }
    }
    
    private fun updateStatus() {
        try {
            val isListening = volunteerManager.isCurrentlyListening()
            val connectionCount = volunteerManager.getActiveConnectionsCount()
            
            val statusText = if (isListening) {
                "Listening for alerts... (Connections: $connectionCount)"
            } else {
                "Not listening"
            }
            
            binding.tvStatus.text = statusText
            
            // Update switch state
            binding.switchListening.isChecked = isListening
            
            Log.d("VolunteerActivity", "Status updated - Listening: $isListening, Connections: $connectionCount")
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error updating status", e)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        try {
            onBackPressed()
            return true
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error navigating up", e)
            return false
        }
    }
    
    override fun onDestroy() {
        try {
            volunteerManager.cleanup()
        } catch (e: Exception) {
            Log.e("VolunteerActivity", "Error in onDestroy", e)
        }
        super.onDestroy()
    }
}
