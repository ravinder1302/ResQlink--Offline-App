package com.rescue.offlineapp.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u0010$\u001a\u00020\u000f2\b\u0010%\u001a\u0004\u0018\u00010\u00182\u0006\u0010&\u001a\u00020\'J\u001a\u0010(\u001a\u00020\u00182\b\u0010%\u001a\u0004\u0018\u00010\u00182\u0006\u0010&\u001a\u00020\'H\u0002J\u0006\u0010)\u001a\u00020*J\u0012\u0010+\u001a\u0004\u0018\u00010\u00182\u0006\u0010,\u001a\u00020\u000bH\u0002J\u0006\u0010-\u001a\u00020.J*\u0010/\u001a\u00020*2\u0006\u0010,\u001a\u00020\u000b2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020*01H\u0082@\u00a2\u0006\u0002\u00103J\u0006\u00104\u001a\u00020\u000fJ2\u00105\u001a\u00020\u000f2\u0006\u00106\u001a\u00020\u00182\u0006\u0010,\u001a\u00020\u000b2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020*01H\u0082@\u00a2\u0006\u0002\u00107J\u001c\u00108\u001a\u00020*2\u0014\b\u0002\u00100\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020*01J\u0006\u00109\u001a\u00020*J\b\u0010:\u001a\u00020*H\u0002J\b\u0010;\u001a\u00020*H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013RN\u0010\u0016\u001aB\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00180\u0018\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u000b0\u000b \u0019* \u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00180\u0018\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u000b0\u000b\u0018\u00010\u001a0\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u001f\u001a\u00020 8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\u0015\u001a\u0004\b!\u0010\"\u00a8\u0006<"}, d2 = {"Lcom/rescue/offlineapp/bluetooth/VolunteerManager;", "", "context", "Landroid/content/Context;", "bluetoothHelper", "Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "database", "Lcom/rescue/offlineapp/data/AppDatabase;", "(Landroid/content/Context;Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;Lcom/rescue/offlineapp/data/AppDatabase;)V", "activeConnections", "", "Landroid/bluetooth/BluetoothSocket;", "gson", "Lcom/google/gson/Gson;", "isListening", "", "mediaPlayer", "Landroid/media/MediaPlayer;", "getMediaPlayer", "()Landroid/media/MediaPlayer;", "mediaPlayer$delegate", "Lkotlin/Lazy;", "pendingAckSockets", "", "", "kotlin.jvm.PlatformType", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "serverSocket", "Landroid/bluetooth/BluetoothServerSocket;", "vibrator", "Landroid/os/Vibrator;", "getVibrator", "()Landroid/os/Vibrator;", "vibrator$delegate", "acceptAlert", "victimId", "timestamp", "", "ackKey", "cleanup", "", "findKeyForSocket", "socket", "getActiveConnectionsCount", "", "handleConnection", "onAlertReceived", "Lkotlin/Function1;", "Lcom/rescue/offlineapp/data/AlertMessage;", "(Landroid/bluetooth/BluetoothSocket;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isCurrentlyListening", "processMessageAndPossiblyHoldSocket", "message", "(Ljava/lang/String;Landroid/bluetooth/BluetoothSocket;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startListening", "stopListening", "stopSOSAlert", "triggerSOSAlert", "app_debug"})
public final class VolunteerManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.rescue.offlineapp.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.BluetoothServerSocket serverSocket;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<android.bluetooth.BluetoothSocket> activeConnections = null;
    private boolean isListening = false;
    private final java.util.Map<java.lang.String, android.bluetooth.BluetoothSocket> pendingAckSockets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vibrator$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy mediaPlayer$delegate = null;
    
    public VolunteerManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper, @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.AppDatabase database) {
        super();
    }
    
    private final android.os.Vibrator getVibrator() {
        return null;
    }
    
    private final android.media.MediaPlayer getMediaPlayer() {
        return null;
    }
    
    public final void startListening(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.rescue.offlineapp.data.AlertMessage, kotlin.Unit> onAlertReceived) {
    }
    
    private final java.lang.Object handleConnection(android.bluetooth.BluetoothSocket socket, kotlin.jvm.functions.Function1<? super com.rescue.offlineapp.data.AlertMessage, kotlin.Unit> onAlertReceived, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object processMessageAndPossiblyHoldSocket(java.lang.String message, android.bluetooth.BluetoothSocket socket, kotlin.jvm.functions.Function1<? super com.rescue.offlineapp.data.AlertMessage, kotlin.Unit> onAlertReceived, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.String ackKey(java.lang.String victimId, long timestamp) {
        return null;
    }
    
    private final java.lang.String findKeyForSocket(android.bluetooth.BluetoothSocket socket) {
        return null;
    }
    
    public final boolean acceptAlert(@org.jetbrains.annotations.Nullable()
    java.lang.String victimId, long timestamp) {
        return false;
    }
    
    private final void triggerSOSAlert() {
    }
    
    private final void stopSOSAlert() {
    }
    
    public final void stopListening() {
    }
    
    public final boolean isCurrentlyListening() {
        return false;
    }
    
    public final int getActiveConnectionsCount() {
        return 0;
    }
    
    public final void cleanup() {
    }
}