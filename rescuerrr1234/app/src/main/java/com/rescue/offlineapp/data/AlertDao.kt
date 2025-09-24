package com.rescue.offlineapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts ORDER BY timestamp DESC")
    fun getAllAlerts(): Flow<List<AlertEntity>>
    
    @Query("SELECT * FROM alerts WHERE isRead = 0 ORDER BY timestamp DESC")
    fun getUnreadAlerts(): Flow<List<AlertEntity>>
    
    @Insert
    suspend fun insertAlert(alert: AlertEntity)
    
    @Update
    suspend fun updateAlert(alert: AlertEntity)
    
    @Delete
    suspend fun deleteAlert(alert: AlertEntity)
    
    @Query("UPDATE alerts SET isRead = 1 WHERE id = :alertId")
    suspend fun markAsRead(alertId: Long)
    
    @Query("DELETE FROM alerts WHERE timestamp < :timestamp")
    suspend fun deleteOldAlerts(timestamp: Long)
}
