package com.rescue.offlineapp.data

import com.google.gson.annotations.SerializedName

data class AlertMessage(
    @SerializedName("type")
    val type: String = "ALERT",
    
    @SerializedName("priority")
    val priority: Priority,
    
    @SerializedName("need")
    val need: String,
    
    @SerializedName("lat")
    val latitude: Double,
    
    @SerializedName("lon")
    val longitude: Double,
    
    @SerializedName("timestamp")
    val timestamp: Long,
    
    @SerializedName("victimId")
    val victimId: String? = null,
    
    @SerializedName("message")
    val message: String? = null
) {
    enum class Priority {
        HIGH, MEDIUM, LOW
    }
    
    companion object {
        const val RESCUE_UUID = "12345678-1234-1234-1234-123456789abc"
    }
}
