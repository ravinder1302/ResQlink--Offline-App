package com.rescue.offlineapp.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018J\u0018\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u001dH\u0002J\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u001fJ\u0006\u0010 \u001a\u00020\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006!"}, d2 = {"Lcom/rescue/offlineapp/location/LocationHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "getFusedLocationClient", "()Lcom/google/android/gms/location/FusedLocationProviderClient;", "fusedLocationClient$delegate", "Lkotlin/Lazy;", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "locationRequest", "Lcom/google/android/gms/location/LocationRequest;", "getLocationRequest", "()Lcom/google/android/gms/location/LocationRequest;", "locationRequest$delegate", "getCurrentLocation", "Landroid/location/Location;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocationStatus", "", "hasLocationPermission", "", "isLocationEnabled", "requestFreshLocation", "", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "startLocationUpdates", "Lkotlinx/coroutines/flow/Flow;", "stopLocationUpdates", "app_debug"})
public final class LocationHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy fusedLocationClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy locationRequest$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.LocationCallback locationCallback = null;
    
    public LocationHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final com.google.android.gms.location.FusedLocationProviderClient getFusedLocationClient() {
        return null;
    }
    
    private final com.google.android.gms.location.LocationRequest getLocationRequest() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentLocation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final void requestFreshLocation(kotlinx.coroutines.CancellableContinuation<? super android.location.Location> continuation) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<android.location.Location> startLocationUpdates() {
        return null;
    }
    
    public final void stopLocationUpdates() {
    }
    
    public final boolean hasLocationPermission() {
        return false;
    }
    
    public final boolean isLocationEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocationStatus() {
        return null;
    }
}