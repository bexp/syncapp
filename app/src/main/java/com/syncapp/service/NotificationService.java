package com.syncapp.service;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.syncapp.R;
import com.syncapp.inject.component.AppComponent;
import com.syncapp.model.Notification;
import com.syncapp.model.SyncEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by bexp on 4/24/16.
 */
public class NotificationService {

    private static final String TAG = NotificationService.class.getSimpleName();

    @Inject
    ApiService apiService;

    @Inject
    Realm realmInstance;

    @Inject
    EventBus mBus;

    @Inject
    Context appContext;

    public NotificationService(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public RealmResults<Notification> getLocalNotifications() {
        return realmInstance.allObjects(Notification.class);
    }

    public void fetchNotificationsAsync() {
        Call<List<Notification>> call = apiService.getNotifications();
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(retrofit.Response<List<Notification>> response, Retrofit retrofit) {
                List<Notification> list = response.body();
                if (list.size() > 0) {
                    realmInstance.beginTransaction();
                    realmInstance.copyToRealmOrUpdate(list);
                    realmInstance.commitTransaction();
                    mBus.post(new SyncEvent(appContext.getString(R.string.synced_event)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure");
                mBus.post(new SyncEvent(appContext.getString(R.string.sync_failed_event)));
                t.printStackTrace();
            }
        });
    }
}
