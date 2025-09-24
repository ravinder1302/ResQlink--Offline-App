package com.rescue.offlineapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val priority: AlertMessage.Priority,
    val need: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val victimId: String?,
    val message: String?,
    val isRead: Boolean = false
)
