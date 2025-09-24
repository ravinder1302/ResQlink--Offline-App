package com.rescue.offlineapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0010H\u0014J\b\u0010\u0019\u001a\u00020\u0012H\u0016J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\b\u0010\u001b\u001a\u00020\u0010H\u0002J\b\u0010\u001c\u001a\u00020\u0010H\u0002J\b\u0010\u001d\u001a\u00020\u0010H\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0012H\u0002J\b\u0010!\u001a\u00020\u0010H\u0002J\b\u0010\"\u001a\u00020\u0010H\u0002J\b\u0010#\u001a\u00020\u0010H\u0002J\u0010\u0010$\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\u0012H\u0002J\b\u0010&\u001a\u00020\u0010H\u0002J\b\u0010\'\u001a\u00020\u0010H\u0002J\b\u0010(\u001a\u00020\u0010H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/rescue/offlineapp/ui/VictimActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "acceptanceFallbackJob", "Lkotlinx/coroutines/Job;", "binding", "Lcom/rescue/offlineapp/databinding/ActivityVictimBinding;", "bluetoothHelper", "Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "bluetoothReceiver", "Landroid/content/BroadcastReceiver;", "locationHelper", "Lcom/rescue/offlineapp/location/LocationHelper;", "victimManager", "Lcom/rescue/offlineapp/bluetooth/VictimManager;", "clearForm", "", "hasLocationPermission", "", "hasRequiredPermissions", "initializeManagers", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onSupportNavigateUp", "registerBluetoothReceiver", "scheduleAcceptanceFallback", "sendCustomAlert", "sendSOS", "sendTestAlert", "setSendingUi", "sending", "setupClickListeners", "setupToolbar", "showPermissionError", "showProgress", "show", "testBluetoothDiscovery", "testLocationOnly", "updateLocation", "app_debug"})
public final class VictimActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.rescue.offlineapp.databinding.ActivityVictimBinding binding;
    private com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper;
    private com.rescue.offlineapp.location.LocationHelper locationHelper;
    private com.rescue.offlineapp.bluetooth.VictimManager victimManager;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job acceptanceFallbackJob;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver bluetoothReceiver = null;
    
    public VictimActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void registerBluetoothReceiver() {
    }
    
    private final void setupToolbar() {
    }
    
    private final void initializeManagers() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void testLocationOnly() {
    }
    
    private final void sendTestAlert() {
    }
    
    private final void testBluetoothDiscovery() {
    }
    
    private final void updateLocation() {
    }
    
    private final void sendSOS() {
    }
    
    private final void sendCustomAlert() {
    }
    
    private final void clearForm() {
    }
    
    private final void showProgress(boolean show) {
    }
    
    private final boolean hasRequiredPermissions() {
        return false;
    }
    
    private final boolean hasLocationPermission() {
        return false;
    }
    
    private final void showPermissionError() {
    }
    
    private final void scheduleAcceptanceFallback() {
    }
    
    private final void setSendingUi(boolean sending) {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}