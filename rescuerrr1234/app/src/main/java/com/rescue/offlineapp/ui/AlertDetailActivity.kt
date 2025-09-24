package com.rescue.offlineapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rescue.offlineapp.R
import com.rescue.offlineapp.data.AppDatabase
import com.rescue.offlineapp.data.AlertEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AlertDetailActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var alertId: Long = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_detail)
        
        alertId = intent.getLongExtra("alert_id", 0)
        if (alertId == 0L) {
            finish()
            return
        }
        
        database = AppDatabase.getDatabase(this)
        setupToolbar()
        loadAlertDetails()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.alert_details)
    }
    
    private fun loadAlertDetails() {
        lifecycleScope.launch {
            // For now, we'll just show a simple layout
            // In a real implementation, you'd load the alert from database
            // and populate the UI with the details
        }
    }
    
    private fun openInMaps(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Fallback to any map app
            val fallbackIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(fallbackIntent)
        }
    }
    
    private fun markAsRead() {
        lifecycleScope.launch {
            database.alertDao().markAsRead(alertId)
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.alert_marked_as_read),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
