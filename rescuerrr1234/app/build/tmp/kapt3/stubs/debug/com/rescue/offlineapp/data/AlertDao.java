package com.rescue.offlineapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/rescue/offlineapp/data/AlertDao;", "", "deleteAlert", "", "alert", "Lcom/rescue/offlineapp/data/AlertEntity;", "(Lcom/rescue/offlineapp/data/AlertEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldAlerts", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAlerts", "Lkotlinx/coroutines/flow/Flow;", "", "getUnreadAlerts", "insertAlert", "markAsRead", "alertId", "updateAlert", "app_debug"})
@androidx.room.Dao()
public abstract interface AlertDao {
    
    @androidx.room.Query(value = "SELECT * FROM alerts ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.rescue.offlineapp.data.AlertEntity>> getAllAlerts();
    
    @androidx.room.Query(value = "SELECT * FROM alerts WHERE isRead = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.rescue.offlineapp.data.AlertEntity>> getUnreadAlerts();
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAlert(@org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AlertEntity alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAlert(@org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AlertEntity alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAlert(@org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AlertEntity alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE alerts SET isRead = 1 WHERE id = :alertId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsRead(long alertId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM alerts WHERE timestamp < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOldAlerts(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}