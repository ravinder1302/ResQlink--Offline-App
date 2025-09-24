package com.rescue.offlineapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u00032\b\b\u0003\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/rescue/offlineapp/data/ServiceNowApi;", "", "createRecord", "Lcom/rescue/offlineapp/data/ServiceNowEnvelope;", "Lcom/rescue/offlineapp/data/HackNowRecord;", "body", "Lcom/rescue/offlineapp/data/HackNowRequest;", "(Lcom/rescue/offlineapp/data/HackNowRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listRecords", "", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ServiceNowApi {
    
    @retrofit2.http.POST(value = "api/now/table/u_hacknow")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createRecord(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.rescue.offlineapp.data.HackNowRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.rescue.offlineapp.data.ServiceNowEnvelope<com.rescue.offlineapp.data.HackNowRecord>> $completion);
    
    @retrofit2.http.GET(value = "api/now/table/u_hacknow")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listRecords(@retrofit2.http.Query(value = "sysparm_limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.rescue.offlineapp.data.ServiceNowEnvelope<java.util.List<com.rescue.offlineapp.data.HackNowRecord>>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}