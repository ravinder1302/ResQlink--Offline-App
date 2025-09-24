package com.rescue.offlineapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0011\u001a\u00020\u000fH\u0002J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u000fH\u0002J\b\u0010\u001b\u001a\u00020\u000fH\u0002J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002J\b\u0010\u001e\u001a\u00020\u000fH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/rescue/offlineapp/ui/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/rescue/offlineapp/databinding/ActivityLoginBinding;", "bluetoothHelper", "Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "enableBluetoothLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "requestPermissionLauncher", "", "", "checkBluetoothAndLocation", "", "checkLocationPermission", "checkPermissions", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "proceedToMainActivity", "requestPermissions", "selectRole", "role", "Lcom/rescue/offlineapp/data/UserRole;", "setupClickListeners", "showBluetoothNotSupportedDialog", "showBluetoothRequiredDialog", "showEnableBluetoothDialog", "showLocationRequiredDialog", "showPermissionDeniedDialog", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.rescue.offlineapp.databinding.ActivityLoginBinding binding;
    private com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> enableBluetoothLauncher = null;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void selectRole(com.rescue.offlineapp.data.UserRole role) {
    }
    
    private final void checkPermissions() {
    }
    
    private final void requestPermissions() {
    }
    
    private final void checkBluetoothAndLocation() {
    }
    
    private final void checkLocationPermission() {
    }
    
    private final void proceedToMainActivity() {
    }
    
    private final void showEnableBluetoothDialog() {
    }
    
    private final void showBluetoothNotSupportedDialog() {
    }
    
    private final void showLocationRequiredDialog() {
    }
    
    private final void showPermissionDeniedDialog() {
    }
    
    private final void showBluetoothRequiredDialog() {
    }
}