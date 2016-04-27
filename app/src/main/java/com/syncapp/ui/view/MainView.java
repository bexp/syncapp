package com.syncapp.ui.view;

import android.support.annotation.NonNull;

import com.syncapp.model.Notification;

import io.realm.RealmResults;

/**
 * Created by bexp on 4/24/16.
 */
public interface MainView {
    void showNotifications(@NonNull RealmResults<Notification> notifications);
    void showSyncMessage(@NonNull String message);
}
