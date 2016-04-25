package com.syncapp.ui.view;

import com.syncapp.model.Notification;

import io.realm.RealmResults;

/**
 * Created by bexp on 4/24/16.
 */
public interface MainView {
    void showNotifications(RealmResults<Notification> notifications);
    void showSyncMessage(String message);
}
