package com.rescue.offlineapp.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rescue.offlineapp.data.AlertMessage
import com.rescue.offlineapp.location.LocationHelper
import com.rescue.offlineapp.data.AppDatabase
import com.rescue.offlineapp.data.AlertEntity
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class VictimManager(
    private val context: Context,
    private val bluetoothHelper: BluetoothHelper,
    private val locationHelper: LocationHelper,
    private val database: AppDatabase
) {
    
    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val connectedSockets = ConcurrentHashMap<String, BluetoothSocket>()
    private val acceptedEventsCount = java.util.concurrent.atomic.AtomicInteger(0)
    private val victimId = UUID.randomUUID().toString()
    private val discoveredDevices = mutableSetOf<BluetoothDevice>()
    @Volatile private var lastDeliveryCount: Int = 0
    @Volatile private var lastAcceptedCount: Int = 0
    @Volatile private var onVolunteerAccepted: (() -> Unit)? = null
    @Volatile private var onDelivered: (() -> Unit)? = null
    @Volatile private var isSending: Boolean = false

    fun isSendInProgress(): Boolean = isSending
    
    fun getLastDeliveryCount(): Int = lastDeliveryCount
    fun getLastAcceptedCount(): Int = lastAcceptedCount
    fun setOnVolunteerAcceptedCallback(callback: (() -> Unit)?) { onVolunteerAccepted = callback }
    fun setOnDeliveredCallback(callback: (() -> Unit)?) { onDelivered = callback }
    
    fun sendAlert(
        priority: AlertMessage.Priority,
        need: String,
        message: String? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (isSending) {
            onError("Another request is already in progress. Please wait...")
            return
        }
        isSending = true
        scope.launch {
            try {
                Log.d("VictimManager", "Starting alert send process...")
                
                // Get current location with tighter timeout
                val location = withTimeoutOrNull(8000) { // 8 second timeout
                    locationHelper.getCurrentLocation()
                }
                
                if (location == null) {
                    Log.e("VictimManager", "Failed to get location within timeout")
                    onError("Unable to get location. Please check GPS and try again.")
                    isSending = false
                    return@launch
                }
                
                Log.d("VictimManager", "Got location: ${location.latitude}, ${location.longitude}")
                
                // Create alert message
                val alertMessage = AlertMessage(
                    priority = priority,
                    need = need,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    timestamp = System.currentTimeMillis(),
                    victimId = victimId,
                    message = message
                )
                
                val jsonMessage = gson.toJson(alertMessage)
                Log.d("VictimManager", "Created alert message: $jsonMessage")

                // Persist locally as an outgoing record
                try {
                    val entity = AlertEntity(
                        priority = alertMessage.priority,
                        need = alertMessage.need,
                        latitude = alertMessage.latitude,
                        longitude = alertMessage.longitude,
                        timestamp = alertMessage.timestamp,
                        victimId = alertMessage.victimId,
                        message = alertMessage.message
                    )
                    database.alertDao().insertAlert(entity)
                    Log.d("VictimManager", "Saved sent alert to local database")
                } catch (e: Exception) {
                    Log.e("VictimManager", "Failed to save sent alert", e)
                }
                
                // Try to send to nearby devices
                val success = tryToSendToNearbyDevices(jsonMessage)
                
                if (success) {
                    Log.d("VictimManager", "Alert sent successfully to at least one device")
                    onSuccess()
                } else {
                    Log.e("VictimManager", "Failed to send alert to any devices")
                    onError("No volunteers found nearby. Make sure volunteers are listening and within range.")
                }
                
            } catch (e: Exception) {
                Log.e("VictimManager", "Error sending alert", e)
                onError("Error sending alert: ${e.message}")
            } finally {
                isSending = false
            }
        }
    }
    
    private suspend fun tryToSendToNearbyDevices(message: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val adapter = bluetoothHelper.bluetoothAdapter
                if (adapter == null || !adapter.isEnabled) {
                    Log.e("VictimManager", "Bluetooth not available or enabled")
                    return@withContext false
                }
                
                Log.d("VictimManager", "Starting aggressive device search...")
                Log.d("VictimManager", "Bluetooth adapter: ${adapter.name}")
                Log.d("VictimManager", "Bluetooth address: ${adapter.address}")
                Log.d("VictimManager", "Bluetooth state: ${adapter.state}")
                Log.d("VictimManager", "Device discoverable: ${adapter.scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE}")
                
                // Note: Making device discoverable requires BLUETOOTH_ADVERTISE permission
                // and can't be done programmatically in newer Android versions
                // Users need to manually enable discoverability in Bluetooth settings
                
                var totalSuccess = false
                var deliveryCount = 0
                var acceptedCount = 0
                
                // Method 1: Try paired devices first (most reliable)
                Log.d("VictimManager", "=== METHOD 1: Trying paired devices ===")
                val pairedDelivered = tryPairedDevices(adapter, message)
                if (pairedDelivered > 0) {
                    Log.d("VictimManager", "✅ Successfully sent to paired device(s)")
                    totalSuccess = true
                    deliveryCount += pairedDelivered
                } else {
                    Log.d("VictimManager", "❌ Failed to send to paired devices")
                }
                
                // Method 2: Aggressive system discovery
                Log.d("VictimManager", "=== METHOD 2: Aggressive system discovery ===")
                val discoveredDelivered = performAggressiveDiscovery(adapter, message)
                if (discoveredDelivered > 0) {
                    Log.d("VictimManager", "✅ Successfully sent to discovered device(s)")
                    totalSuccess = true
                    deliveryCount += discoveredDelivered
                } else {
                    Log.d("VictimManager", "❌ Failed to send to discovered devices")
                }
                
                // Method 3: Try to connect to any visible device with multiple strategies
                Log.d("VictimManager", "=== METHOD 3: Multi-strategy connection attempts ===")
                val multiDelivered = tryMultiStrategyConnections(adapter, message)
                if (multiDelivered > 0) {
                    Log.d("VictimManager", "✅ Successfully sent using multi-strategy")
                    totalSuccess = true
                    deliveryCount += multiDelivered
                } else {
                    Log.d("VictimManager", "❌ Failed using multi-strategy")
                }
                
                // Count accepts captured during send attempts
                acceptedCount = acceptedEventsCount.getAndSet(0)
                
                Log.d("VictimManager", "=== FINAL RESULT ===")
                Log.d("VictimManager", "Device search completed. Success: $totalSuccess")
                Log.d("VictimManager", "Total discovered devices: ${discoveredDevices.size}")
                discoveredDevices.forEach { device ->
                    Log.d("VictimManager", "  - ${device.name ?: "Unknown"} (${device.address}) - Bond state: ${device.bondState}")
                }
                lastDeliveryCount = deliveryCount
                lastAcceptedCount = acceptedCount
                totalSuccess
                
            } catch (e: Exception) {
                Log.e("VictimManager", "Error in device search", e)
                false
            }
        }
    }
    
    private suspend fun tryPairedDevices(adapter: BluetoothAdapter, message: String): Int {
        return try {
            Log.d("VictimManager", "Trying paired devices...")
            val pairedDevices = adapter.bondedDevices
            
            if (pairedDevices.isEmpty()) {
                Log.d("VictimManager", "No paired devices found")
                return 0
            }
            
            Log.d("VictimManager", "Found ${pairedDevices.size} paired devices")
            
            var successfulSends = 0
            for (device in pairedDevices) {
                try {
                    Log.d("VictimManager", "Trying paired device: ${device.name ?: device.address}")
                    if (sendToDevice(device, message)) {
                        successfulSends++
                        Log.d("VictimManager", "Successfully sent to paired device: ${device.name}")
                    }
                } catch (e: Exception) {
                    Log.e("VictimManager", "Error sending to paired device ${device.name}", e)
                }
            }
            
            Log.d("VictimManager", "Successfully sent to $successfulSends out of ${pairedDevices.size} paired devices")
            successfulSends
            
        } catch (e: Exception) {
            Log.e("VictimManager", "Error trying paired devices", e)
            0
        }
    }
    
    private suspend fun performAggressiveDiscovery(adapter: BluetoothAdapter, message: String): Int {
        return try {
            Log.d("VictimManager", "Starting latency-optimized discovery...")

            var delivered = 0

            // Run up to 2 short discovery cycles (about ~8s each)
            repeat(2) { cycleIndex ->
                if (delivered > 0) return@repeat

                // Cancel any ongoing discovery
                if (adapter.isDiscovering) {
                    adapter.cancelDiscovery()
                    delay(500)
                }

                // Start discovery
                val started = adapter.startDiscovery()
                if (!started) {
                    Log.w("VictimManager", "Discovery did not start (cycle ${cycleIndex + 1})")
                    return@repeat
                }

                Log.d("VictimManager", "Discovery cycle ${cycleIndex + 1} started")
                // Wait a short window for results to arrive via BroadcastReceiver
                delay(8000)

                // Stop discovery before attempting any connections
                adapter.cancelDiscovery()

                if (discoveredDevices.isEmpty()) {
                    Log.d("VictimManager", "No devices found in cycle ${cycleIndex + 1}")
                    return@repeat
                }

                Log.d("VictimManager", "Found ${discoveredDevices.size} devices in cycle ${cycleIndex + 1}. Attempting sends...")

                // Attempt to send to all discovered devices; do not exit at first success
                for (device in discoveredDevices) {
                    try {
                        // Ensure discovery is stopped before connecting
                        if (adapter.isDiscovering) adapter.cancelDiscovery()

                        Log.d("VictimManager", "Trying discovered device: ${device.name ?: device.address}")
                        val sentSecure = sendToDevice(device, message)
                        val sentInsecure = if (!sentSecure) sendToDeviceInsecure(device, message) else false
                        if (sentSecure || sentInsecure) {
                            Log.d("VictimManager", "Successfully sent to: ${device.name}")
                            delivered++
                        }
                    } catch (e: Exception) {
                        Log.e("VictimManager", "Error sending to discovered device ${device.name}", e)
                    }
                }
            }

            delivered
            
        } catch (e: Exception) {
            Log.e("VictimManager", "Error in aggressive discovery", e)
            0
        }
    }
    
    private suspend fun tryMultiStrategyConnections(adapter: BluetoothAdapter, message: String): Int {
        return try {
            Log.d("VictimManager", "Trying multi-strategy connections...")
            
            var delivered = 0
            
            for (device in discoveredDevices) {
                try {
                    Log.d("VictimManager", "Trying multi-strategy on device: ${device.name ?: device.address}")
                    
                    // Strategy 1: Try with our specific UUID
                    if (sendToDevice(device, message)) {
                        delivered++
                        Log.d("VictimManager", "✅ Strategy 1 succeeded for ${device.name}")
                        continue
                    }
                    
                    // Strategy 2: Try with insecure RFCOMM (more permissive)
                    if (sendToDeviceInsecure(device, message)) {
                        delivered++
                        Log.d("VictimManager", "✅ Strategy 2 succeeded for ${device.name}")
                        continue
                    }
                    
                    // Strategy 3: Try with a common UUID (some devices might accept this)
                    if (sendToDeviceWithCommonUUID(device, message)) {
                        delivered++
                        Log.d("VictimManager", "✅ Strategy 3 succeeded for ${device.name}")
                        continue
                    }
                    
                    Log.d("VictimManager", "❌ All strategies failed for ${device.name}")
                    
                } catch (e: Exception) {
                    Log.e("VictimManager", "Error in multi-strategy for ${device.name}", e)
                }
            }
            
            delivered
            
        } catch (e: Exception) {
            Log.e("VictimManager", "Error in multi-strategy connections", e)
            0
        }
    }
    
    private suspend fun sendToDeviceInsecure(device: BluetoothDevice, message: String): Boolean {
        return withContext(Dispatchers.IO) {
            var socket: BluetoothSocket? = null
            try {
                Log.d("VictimManager", "  - 🔌 Trying insecure connection to: ${device.name}")
                
                // Try insecure RFCOMM (more permissive)
                socket = device.createInsecureRfcommSocketToServiceRecord(
                    UUID.fromString(AlertMessage.RESCUE_UUID)
                )
                
                Log.d("VictimManager", "  - Insecure socket created, attempting connection...")
                
                withTimeoutOrNull(3000) {
                    socket.connect()
                } ?: run {
                    Log.w("VictimManager", "  - ❌ Insecure connection timeout for ${device.name}")
                    return@withContext false
                }
                
                Log.d("VictimManager", "  - ✅ Insecure connection successful to ${device.name}")
                
                // Send message
                val outputStream = socket.outputStream
                outputStream.write(message.toByteArray())
                outputStream.flush()
                
                Log.d("VictimManager", "  - ✅ Message sent via insecure connection to ${device.name}")
                
                delay(500)
                true
                
            } catch (e: Exception) {
                Log.e("VictimManager", "  - ❌ Insecure connection failed for ${device.name}: ${e.message}")
                false
            } finally {
                try {
                    socket?.close()
                } catch (e: IOException) {
                    Log.e("VictimManager", "Error closing insecure socket", e)
                }
            }
        }
    }
    
    private suspend fun sendToDeviceWithCommonUUID(device: BluetoothDevice, message: String): Boolean {
        return withContext(Dispatchers.IO) {
            var socket: BluetoothSocket? = null
            try {
                Log.d("VictimManager", "  - 🔌 Trying common UUID connection to: ${device.name}")
                
                // Try with a common UUID that some devices might accept
                val commonUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Serial Port Profile
                
                socket = device.createRfcommSocketToServiceRecord(commonUUID)
                
                Log.d("VictimManager", "  - Common UUID socket created, attempting connection...")
                
                withTimeoutOrNull(3000) {
                    socket.connect()
                } ?: run {
                    Log.w("VictimManager", "  - ❌ Common UUID connection timeout for ${device.name}")
                    return@withContext false
                }
                
                Log.d("VictimManager", "  - ✅ Common UUID connection successful to ${device.name}")
                
                // Send message
                val outputStream = socket.outputStream
                outputStream.write(message.toByteArray())
                outputStream.flush()
                
                Log.d("VictimManager", "  - ✅ Message sent via common UUID to ${device.name}")
                
                delay(500)
                true
                
            } catch (e: Exception) {
                Log.e("VictimManager", "  - ❌ Common UUID connection failed for ${device.name}: ${e.message}")
                false
            } finally {
                try {
                    socket?.close()
                } catch (e: IOException) {
                    Log.e("VictimManager", "Error closing common UUID socket", e)
                }
            }
        }
    }
    
    private suspend fun sendToDevice(device: BluetoothDevice, message: String): Boolean {
        return withContext(Dispatchers.IO) {
            var socket: BluetoothSocket? = null
            var handedOff = false
            try {
                Log.d("VictimManager", "🔌 Attempting connection to device: ${device.name ?: "Unknown"} (${device.address})")
                Log.d("VictimManager", "  - Device class: ${device.bluetoothClass}")
                Log.d("VictimManager", "  - Bond state: ${device.bondState}")
                Log.d("VictimManager", "  - Device type: ${device.type}")
                
                // Try to create socket with our service UUID
                Log.d("VictimManager", "  - Creating RFCOMM socket with UUID: ${AlertMessage.RESCUE_UUID}")
                socket = device.createRfcommSocketToServiceRecord(
                    UUID.fromString(AlertMessage.RESCUE_UUID)
                )
                
                Log.d("VictimManager", "  - Socket created, attempting connection...")
                
                // Set connection timeout
                withTimeoutOrNull(3000) { // 3 second timeout
                    socket.connect()
                } ?: run {
                    Log.w("VictimManager", "  - ❌ Connection timeout for device: ${device.name}")
                    return@withContext false
                }
                
                Log.d("VictimManager", "  - ✅ Connected to device: ${device.name}")
                connectedSockets[device.address] = socket
                
                // Send message
                Log.d("VictimManager", "  - 📤 Sending message...")
                val outputStream = socket.outputStream
                val inputStream = socket.inputStream
                outputStream.write(message.toByteArray())
                outputStream.flush()
                
                Log.d("VictimManager", "  - ✅ Message sent successfully to device: ${device.name}")

                // Wait for initial ACK from volunteer
                val ackBuffer = ByteArray(64)
                val initialAck = withTimeoutOrNull(3000) {
                    val read = inputStream.read(ackBuffer)
                    if (read > 0) String(ackBuffer, 0, read) else null
                }
                if (initialAck == "ACK" || initialAck == "ACK_ACCEPTED") {
                    Log.d("VictimManager", "  - ✅ Initial ACK received from ${device.name}: $initialAck")
                    // Notify UI that delivery was confirmed so loading can stop
                    onDelivered?.let { cb ->
                        scope.launch(Dispatchers.Main) { cb.invoke() }
                    }
                    if (initialAck == "ACK_ACCEPTED") {
                        acceptedEventsCount.incrementAndGet()
                        // Notify UI immediately
                        onVolunteerAccepted?.let { cb ->
                            scope.launch(Dispatchers.Main) { cb.invoke() }
                        }
                    } else {
                        // Continue listening for ACCEPTED for a short window
                        handedOff = true
                        val handedSocket = socket
                        scope.launch(Dispatchers.IO) {
                            try {
                                withTimeout(60000) {
                                    while (isActive) {
                                        val more = ByteArray(64)
                                        val r = handedSocket.inputStream.read(more)
                                        if (r > 0) {
                                            val resp = String(more, 0, r)
                                            Log.d("VictimManager", "  - 📥 Further response from ${device.name}: $resp")
                                            if (resp == "ACK_ACCEPTED") {
                                                acceptedEventsCount.incrementAndGet()
                                                onVolunteerAccepted?.let { cb ->
                                                    launch(Dispatchers.Main) { cb.invoke() }
                                                }
                                                break
                                            }
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                Log.e("VictimManager", "  - Error while waiting for ACK_ACCEPTED from ${device.name}", e)
                            } finally {
                                try { handedSocket.close() } catch (_: Exception) {}
                                connectedSockets.remove(device.address)
                                Log.d("VictimManager", "  - 🔌 Handed-off socket closed")
                            }
                        }
                    }
                } else {
                    Log.d("VictimManager", "  - ⚠️ No initial ACK received from ${device.name}")
                }

                // If we handed off the socket to a background waiter, return success now
                if (handedOff) return@withContext true

                // Otherwise, small delay and close here
                delay(200)
                true
                
            } catch (e: Exception) {
                Log.e("VictimManager", "  - ❌ Error sending to device ${device.name}: ${e.message}")
                Log.e("VictimManager", "  - Exception type: ${e.javaClass.simpleName}")
                false
            } finally {
                try {
                    // Do not close here if the socket was handed off to the background listener
                    if (!handedOff) {
                        if (socket != null && connectedSockets[device.address] == socket) {
                            socket.close()
                            connectedSockets.remove(device.address)
                            Log.d("VictimManager", "  - 🔌 Socket closed and cleaned up")
                        }
                    } else {
                        Log.d("VictimManager", "  - ⏳ Socket left open for ACK_ACCEPTED listener")
                    }
                } catch (e: IOException) {
                    Log.e("VictimManager", "  - Error closing socket", e)
                }
            }
        }
    }
    
    // Method to add discovered devices (called from activity)
    fun addDiscoveredDevice(device: BluetoothDevice) {
        discoveredDevices.add(device)
        Log.d("VictimManager", "Added discovered device: ${device.name ?: device.address}")
    }
    
    fun cleanup() {
        try {
            scope.cancel()
            connectedSockets.values.forEach { socket ->
                try {
                    socket.close()
                } catch (e: IOException) {
                    Log.e("VictimManager", "Error closing socket", e)
                }
            }
            connectedSockets.clear()
            discoveredDevices.clear()
            Log.d("VictimManager", "Cleanup completed")
        } catch (e: Exception) {
            Log.e("VictimManager", "Error during cleanup", e)
        }
    }
}
