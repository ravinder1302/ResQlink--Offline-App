package com.rescue.offlineapp.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rescue.offlineapp.data.AlertEntity
import com.rescue.offlineapp.data.AlertMessage
import com.rescue.offlineapp.data.AppDatabase
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

class VolunteerManager(
    private val context: Context,
    private val bluetoothHelper: BluetoothHelper,
    private val database: AppDatabase
) {
    
    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var serverSocket: BluetoothServerSocket? = null
    private val activeConnections = mutableListOf<BluetoothSocket>()
    private var isListening = false
    private val pendingAckSockets = Collections.synchronizedMap(mutableMapOf<String, BluetoothSocket>())
    
    private val vibrator: Vibrator by lazy {
        val vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        vibrator
    }
    
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
            )
        }
    }
    
    fun startListening(onAlertReceived: (AlertMessage) -> Unit = {}) {
        if (isListening) {
            Log.d("VolunteerManager", "Already listening, skipping start")
            return
        }
        
        scope.launch {
            try {
                val adapter = bluetoothHelper.bluetoothAdapter
                if (adapter == null || !adapter.isEnabled) {
                    Log.e("VolunteerManager", "Bluetooth not available or enabled")
                    return@launch
                }
                
                Log.d("VolunteerManager", "Creating server socket with UUID: ${AlertMessage.RESCUE_UUID}")
                
                // Note: Making device discoverable requires BLUETOOTH_ADVERTISE permission
                // and can't be done programmatically in newer Android versions
                // Users need to manually enable discoverability in Bluetooth settings
                
                // Try to create server socket with multiple strategies
                serverSocket = try {
                    Log.d("VolunteerManager", "Trying secure RFCOMM server socket...")
                    adapter.listenUsingRfcommWithServiceRecord(
                        "RescueService",
                        UUID.fromString(AlertMessage.RESCUE_UUID)
                    )
                } catch (e: Exception) {
                    Log.e("VolunteerManager", "Failed to create secure server socket", e)
                    // Try alternative method
                    try {
                        Log.d("VolunteerManager", "Trying insecure RFCOMM server socket...")
                        adapter.listenUsingInsecureRfcommWithServiceRecord(
                            "RescueService",
                            UUID.fromString(AlertMessage.RESCUE_UUID)
                        )
                    } catch (e2: Exception) {
                        Log.e("VolunteerManager", "Failed to create insecure server socket too", e2)
                        // Try one more fallback
                        try {
                            Log.d("VolunteerManager", "Trying fallback server socket...")
                            adapter.listenUsingRfcommWithServiceRecord(
                                "RescueService",
                                UUID.randomUUID() // Use random UUID as fallback
                            )
                        } catch (e3: Exception) {
                            Log.e("VolunteerManager", "All server socket creation methods failed", e3)
                            return@launch
                        }
                    }
                }
                
                isListening = true
                Log.d("VolunteerManager", "Started listening for connections on server socket")
                
                while (isListening) {
                    try {
                        Log.d("VolunteerManager", "Waiting for incoming connection...")
                        val socket = serverSocket?.accept()
                        if (socket != null) {
                            val remoteDevice = socket.remoteDevice
                            Log.d("VolunteerManager", "New connection accepted from: ${remoteDevice.name ?: remoteDevice.address}")
                            Log.d("VolunteerManager", "  - Device class: ${remoteDevice.bluetoothClass}")
                            Log.d("VolunteerManager", "  - Bond state: ${remoteDevice.bondState}")
                            Log.d("VolunteerManager", "  - Device type: ${remoteDevice.type}")
                            
                            activeConnections.add(socket)
                            
                            // Handle connection in a separate coroutine
                            launch {
                                handleConnection(socket, onAlertReceived)
                            }
                        }
                    } catch (e: IOException) {
                        if (isListening) {
                            Log.e("VolunteerManager", "Error accepting connection", e)
                            // Brief delay before trying again
                            delay(1000)
                        }
                    } catch (e: Exception) {
                        if (isListening) {
                            Log.e("VolunteerManager", "Unexpected error accepting connection", e)
                            delay(1000)
                        }
                    }
                }
                
            } catch (e: Exception) {
                Log.e("VolunteerManager", "Error starting server", e)
                isListening = false
            }
        }
    }
    
    private suspend fun handleConnection(
        socket: BluetoothSocket,
        onAlertReceived: (AlertMessage) -> Unit
    ) {
        withContext(Dispatchers.IO) {
                var keepOpen = false
            try {
                Log.d("VolunteerManager", "Handling connection from: ${socket.remoteDevice.name}")
                
                val inputStream = socket.inputStream
                val buffer = ByteArray(2048) // Increased buffer size
                val bytes = inputStream.read(buffer)
                
                if (bytes > 0) {
                    val message = String(buffer, 0, bytes)
                    Log.d("VolunteerManager", "Received message: $message")
                    keepOpen = processMessageAndPossiblyHoldSocket(message, socket, onAlertReceived)
                    if (keepOpen) {
                        // Keep the socket open pending user acceptance; add basic timeout cleanup
                        scope.launch {
                            delay(60000)
                            val key = findKeyForSocket(socket)
                            if (key != null && pendingAckSockets[key] == socket) {
                                try {
                                    pendingAckSockets.remove(key)
                                    socket.close()
                                    activeConnections.remove(socket)
                                    Log.d("VolunteerManager", "Pending ACK socket timed out (60s) and closed for key=$key")
                                } catch (e: Exception) {
                                    Log.e("VolunteerManager", "Error closing timed out socket", e)
                                }
                            }
                        }
                        return@withContext
                    }
                } else {
                    Log.w("VolunteerManager", "No data received from connection")
                }
                
            } catch (e: Exception) {
                Log.e("VolunteerManager", "Error handling connection from ${socket.remoteDevice.name}", e)
            } finally {
                try {
                    if (!keepOpen) {
                        socket.close()
                        activeConnections.remove(socket)
                        Log.d("VolunteerManager", "Connection closed and removed from active connections")
                    } else {
                        Log.d("VolunteerManager", "Connection kept open awaiting acceptance")
                    }
                } catch (e: IOException) {
                    Log.e("VolunteerManager", "Error closing socket", e)
                }
            }
        }
    }
    
    private suspend fun processMessageAndPossiblyHoldSocket(
        message: String,
        socket: BluetoothSocket,
        onAlertReceived: (AlertMessage) -> Unit
    ): Boolean {
        try {
            Log.d("VolunteerManager", "Processing message: $message")
            
            val alertMessage = gson.fromJson(message, AlertMessage::class.java)
            
            if (alertMessage.type == "ALERT") {
                Log.d("VolunteerManager", "Valid alert received: ${alertMessage.need}")
                
                // Save to database
                val alertEntity = AlertEntity(
                    priority = alertMessage.priority,
                    need = alertMessage.need,
                    latitude = alertMessage.latitude,
                    longitude = alertMessage.longitude,
                    timestamp = alertMessage.timestamp,
                    victimId = alertMessage.victimId,
                    message = alertMessage.message
                )
                
                database.alertDao().insertAlert(alertEntity)
                Log.d("VolunteerManager", "Alert saved to database")
                
                // Trigger notifications
                triggerSOSAlert()
                
                // Notify UI
                onAlertReceived(alertMessage)
                
                // Send immediate ACK to victim to confirm receipt
                try {
                    val out = socket.outputStream
                    out.write("ACK".toByteArray())
                    out.flush()
                    Log.d("VolunteerManager", "Initial ACK sent to victim")
                } catch (e: Exception) {
                    Log.e("VolunteerManager", "Failed to send initial ACK", e)
                }

                // Hold socket for user acceptance
                val key = ackKey(alertMessage.victimId, alertMessage.timestamp)
                pendingAckSockets[key] = socket
                Log.d("VolunteerManager", "Socket held for pending ACK with key=$key")
                Log.d("VolunteerManager", "Alert processing completed successfully; awaiting user acceptance")
                return true
            } else {
                Log.w("VolunteerManager", "Received non-alert message type: ${alertMessage.type}")
            }
            
        } catch (e: JsonSyntaxException) {
            Log.e("VolunteerManager", "Error parsing JSON message: $message", e)
        } catch (e: Exception) {
            Log.e("VolunteerManager", "Error processing message: $message", e)
        }
        return false
    }

    private fun ackKey(victimId: String?, timestamp: Long): String = "${victimId ?: ""}:$timestamp"

    private fun findKeyForSocket(socket: BluetoothSocket): String? {
        return pendingAckSockets.entries.firstOrNull { it.value == socket }?.key
    }

    fun acceptAlert(victimId: String?, timestamp: Long): Boolean {
        return try {
            val key = ackKey(victimId, timestamp)
            val socket = pendingAckSockets.remove(key)
            if (socket != null) {
                try {
                    stopSOSAlert()
                    val output = socket.outputStream
                    output.write("ACK_ACCEPTED".toByteArray())
                    output.flush()
                    socket.close()
                    activeConnections.remove(socket)
                    Log.d("VolunteerManager", "Sent ACK_ACCEPTED and closed socket for key=$key")
                    true
                } catch (e: Exception) {
                    Log.e("VolunteerManager", "Error sending ACK_ACCEPTED", e)
                    try { socket.close() } catch (_: Exception) {}
                    activeConnections.remove(socket)
                    false
                }
            } else {
                Log.w("VolunteerManager", "No pending socket found for key=$key")
                false
            }
        } catch (e: Exception) {
            Log.e("VolunteerManager", "acceptAlert error", e)
            false
        }
    }
    
    private fun triggerSOSAlert() {
        try {
            Log.d("VolunteerManager", "Triggering SOS alert")
            
            // Stop any ongoing vibration and do not start new vibration
            try {
                vibrator.cancel()
            } catch (e: Exception) {
                Log.e("VolunteerManager", "Error cancelling vibration", e)
            }
            
            // Play sound (you can add a sound file to resources)
            try {
                // For now, just set volume to max
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0)
                Log.d("VolunteerManager", "SOS alert triggered successfully")
            } catch (e: Exception) {
                Log.e("VolunteerManager", "Error playing sound", e)
            }
        } catch (e: Exception) {
            Log.e("VolunteerManager", "Error triggering SOS alert", e)
        }
    }

    private fun stopSOSAlert() {
        try {
            vibrator.cancel()
        } catch (e: Exception) {
            Log.e("VolunteerManager", "Error stopping SOS alert", e)
        }
    }
    
    fun stopListening() {
        Log.d("VolunteerManager", "Stopping listening...")
        isListening = false
        
        try {
            serverSocket?.close()
            serverSocket = null
            Log.d("VolunteerManager", "Server socket closed")
        } catch (e: IOException) {
            Log.e("VolunteerManager", "Error closing server socket", e)
        }
        
        activeConnections.forEach { socket ->
            try {
                socket.close()
            } catch (e: IOException) {
                Log.e("VolunteerManager", "Error closing connection", e)
            }
        }
        activeConnections.clear()
        
        Log.d("VolunteerManager", "Stopped listening for connections")
    }
    
    fun isCurrentlyListening(): Boolean {
        return isListening
    }
    
    fun getActiveConnectionsCount(): Int {
        return activeConnections.size
    }
    
    fun cleanup() {
        Log.d("VolunteerManager", "Starting cleanup...")
        stopListening()
        scope.cancel()
        
        try {
            mediaPlayer.release()
        } catch (e: Exception) {
            Log.e("VolunteerManager", "Error releasing media player", e)
        }
        
        Log.d("VolunteerManager", "Cleanup completed")
    }
}
