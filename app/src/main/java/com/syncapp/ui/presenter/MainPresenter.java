package com.syncapp.ui.presenter;

import com.syncapp.inject.component.AppComponent;
import com.syncapp.model.SyncEvent;
import com.syncapp.service.NotificationService;
import com.syncapp.ui.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by bexp on 4/24/16.
 */
public class MainPresenter {
    @Inject
    NotificationService mService;

    @Inject
    EventBus mBus;

    private MainView view;

    public MainPresenter(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public void onResume() {
        mBus.register(this);
    }
    public void onPause(){
        mBus.unregister(this);
    }

    public void setView(MainView view) {
        this.view = view;
        this.view.showNotifications(mService.getLocalNotifications());
        mService.fetchNotificationsAsync();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SyncEvent event){
        view.showSyncMessage(event.getMessage());
    }
}
