package com.rescue.offlineapp.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\u0002\u0010\u0013J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0015R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "getBluetoothAdapter", "()Landroid/bluetooth/BluetoothAdapter;", "bluetoothAdapter$delegate", "Lkotlin/Lazy;", "bluetoothManager", "Landroid/bluetooth/BluetoothManager;", "getBluetoothManager", "()Landroid/bluetooth/BluetoothManager;", "bluetoothManager$delegate", "getRequiredPermissions", "", "", "()[Ljava/lang/String;", "hasBluetoothPermissions", "", "isBluetoothEnabled", "isBluetoothSupported", "app_debug"})
public final class BluetoothHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy bluetoothManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy bluetoothAdapter$delegate = null;
    
    public BluetoothHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.bluetooth.BluetoothManager getBluetoothManager() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.bluetooth.BluetoothAdapter getBluetoothAdapter() {
        return null;
    }
    
    public final boolean isBluetoothSupported() {
        return false;
    }
    
    public final boolean isBluetoothEnabled() {
        return false;
    }
    
    public final boolean hasBluetoothPermissions() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getRequiredPermissions() {
        return null;
    }
}