package com.syncapp.service;

import java.util.List;
import retrofit.Call;
import retrofit.http.GET;
import com.syncapp.model.Notification;

/**
 * Created by bexp on 4/22/16.
 */
public interface ApiService {
    @GET("v2/mp/debug/notifications")
    Call<List<Notification>> getNotifications();
}