package com.rescue.offlineapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0012\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u000eH\u0014J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0002J\b\u0010\u0017\u001a\u00020\u000eH\u0002J\b\u0010\u0018\u001a\u00020\u000eH\u0002J\b\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u0010\u001a\u001a\u00020\u000eH\u0002J\u0016\u0010\u001b\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/rescue/offlineapp/ui/VolunteerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "alertAdapter", "Lcom/rescue/offlineapp/ui/adapter/AlertAdapter;", "binding", "Lcom/rescue/offlineapp/databinding/ActivityVolunteerBinding;", "bluetoothHelper", "Lcom/rescue/offlineapp/bluetooth/BluetoothHelper;", "database", "Lcom/rescue/offlineapp/data/AppDatabase;", "volunteerManager", "Lcom/rescue/offlineapp/bluetooth/VolunteerManager;", "initializeManagers", "", "observeAlerts", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onSupportNavigateUp", "", "setupClickListeners", "setupRecyclerView", "setupToolbar", "startListening", "stopListening", "updateAlertsList", "alerts", "", "Lcom/rescue/offlineapp/data/AlertEntity;", "updateStatus", "app_debug"})
public final class VolunteerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.rescue.offlineapp.databinding.ActivityVolunteerBinding binding;
    private com.rescue.offlineapp.bluetooth.BluetoothHelper bluetoothHelper;
    private com.rescue.offlineapp.bluetooth.VolunteerManager volunteerManager;
    private com.rescue.offlineapp.data.AppDatabase database;
    private com.rescue.offlineapp.ui.adapter.AlertAdapter alertAdapter;
    
    public VolunteerActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void initializeManagers() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void observeAlerts() {
    }
    
    private final void updateAlertsList(java.util.List<com.rescue.offlineapp.data.AlertEntity> alerts) {
    }
    
    private final void startListening() {
    }
    
    private final void stopListening() {
    }
    
    private final void updateStatus() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}