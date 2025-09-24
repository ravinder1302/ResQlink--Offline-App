package com.rescue.offlineapp.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u0013J\u0006\u0010*\u001a\u00020\u001dJ\u0006\u0010+\u001a\u00020\u0019J\u0006\u0010,\u001a\u00020\u0019J\u0006\u0010-\u001a\u00020\u0017J\u001e\u0010.\u001a\u00020\u00192\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u00102JH\u00103\u001a\u00020\u001d2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u000f2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u00107\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0014\b\u0002\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001d09J\u001e\u0010:\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u00132\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010;J\u001e\u0010<\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u00132\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010;J\u001e\u0010=\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u00132\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010;J\u0016\u0010>\u001a\u00020\u001d2\u000e\u0010?\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cJ\u0016\u0010@\u001a\u00020\u001d2\u000e\u0010?\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cJ\u001e\u0010A\u001a\u00020\u00192\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u00102J\u001e\u0010B\u001a\u00020\u00192\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u00102J\u0016\u0010C\u001a\u00020\u00172\u0006\u00101\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010DR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010!\u001a\u00020\"8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b#\u0010$R\u000e\u0010\'\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006E"}, d2 = {"Lcom/rescue/offlineapp/bluetooth/VictimManager;", "", "context", "Landroid/content/Context;", "bluetoothHelper", "Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "locationHelper", "Lcom/rescue/offlineapp/location/LocationHelper;", "database", "Lcom/rescue/offlineapp/data/AppDatabase;", "(Landroid/content/Context;Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;Lcom/rescue/offlineapp/location/LocationHelper;Lcom/rescue/offlineapp/data/AppDatabase;)V", "acceptedEventsCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "connectedSockets", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Landroid/bluetooth/BluetoothSocket;", "discoveredDevices", "", "Landroid/bluetooth/BluetoothDevice;", "gson", "Lcom/google/gson/Gson;", "isSending", "", "lastAcceptedCount", "", "lastDeliveryCount", "onDelivered", "Lkotlin/Function0;", "", "onVolunteerAccepted", "scope", "Lkotlinx/coroutines/CoroutineScope;", "serviceNowApi", "Lcom/rescue/offlineapp/data/ServiceNowApi;", "getServiceNowApi", "()Lcom/rescue/offlineapp/data/ServiceNowApi;", "serviceNowApi$delegate", "Lkotlin/Lazy;", "victimId", "addDiscoveredDevice", "device", "cleanup", "getLastAcceptedCount", "getLastDeliveryCount", "isSendInProgress", "performAggressiveDiscovery", "adapter", "Landroid/bluetooth/BluetoothAdapter;", "message", "(Landroid/bluetooth/BluetoothAdapter;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendAlert", "priority", "Lcom/rescue/offlineapp/data/AlertMessage$Priority;", "need", "onSuccess", "onError", "Lkotlin/Function1;", "sendToDevice", "(Landroid/bluetooth/BluetoothDevice;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendToDeviceInsecure", "sendToDeviceWithCommonUUID", "setOnDeliveredCallback", "callback", "setOnVolunteerAcceptedCallback", "tryMultiStrategyConnections", "tryPairedDevices", "tryToSendToNearbyDevices", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class VictimManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.rescue.offlineapp.location.LocationHelper locationHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.rescue.offlineapp.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.bluetooth.BluetoothSocket> connectedSockets = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicInteger acceptedEventsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String victimId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<android.bluetooth.BluetoothDevice> discoveredDevices = null;
    @kotlin.jvm.Volatile()
    private volatile int lastDeliveryCount = 0;
    @kotlin.jvm.Volatile()
    private volatile int lastAcceptedCount = 0;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile kotlin.jvm.functions.Function0<kotlin.Unit> onVolunteerAccepted;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile kotlin.jvm.functions.Function0<kotlin.Unit> onDelivered;
    @kotlin.jvm.Volatile()
    private volatile boolean isSending = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy serviceNowApi$delegate = null;
    
    public VictimManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper, @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.location.LocationHelper locationHelper, @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AppDatabase database) {
        super();
    }
    
    private final com.rescue.offlineapp.data.ServiceNowApi getServiceNowApi() {
        return null;
    }
    
    public final boolean isSendInProgress() {
        return false;
    }
    
    public final int getLastDeliveryCount() {
        return 0;
    }
    
    public final int getLastAcceptedCount() {
        return 0;
    }
    
    public final void setOnVolunteerAcceptedCallback(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> callback) {
    }
    
    public final void setOnDeliveredCallback(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> callback) {
    }
    
    public final void sendAlert(@org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AlertMessage.Priority priority, @org.jetbrains.annotations.NotNull()
    java.lang.String need, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    private final java.lang.Object tryToSendToNearbyDevices(java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object tryPairedDevices(android.bluetooth.BluetoothAdapter adapter, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object performAggressiveDiscovery(android.bluetooth.BluetoothAdapter adapter, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object tryMultiStrategyConnections(android.bluetooth.BluetoothAdapter adapter, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object sendToDeviceInsecure(android.bluetooth.BluetoothDevice device, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object sendToDeviceWithCommonUUID(android.bluetooth.BluetoothDevice device, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object sendToDevice(android.bluetooth.BluetoothDevice device, java.lang.String message, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    public final void addDiscoveredDevice(@org.jetbrains.annotations.NotNull()
    android.bluetooth.BluetoothDevice device) {
    }
    
    public final void cleanup() {
    }
}