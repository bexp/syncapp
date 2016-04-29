package com.syncapp.inject.module;

import android.content.Context;

import com.syncapp.App;
import com.syncapp.service.ApiService;
import com.syncapp.service.NotificationService;
import com.syncapp.ui.presenter.MainPresenter;
import org.greenrobot.eventbus.EventBus;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

import static org.mockito.Mockito.mock;

/**
 * Created by bexp on 4/29/16.
 */

@Module
public class TestModule {
    private App app;

    public TestModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    public ApiService apiService() {
        return mock(ApiService.class);
    }

    @Provides
    @Singleton
    NotificationService notificationService() {
        return new NotificationService(app.getComponent());
    }

    @Provides
    @Singleton
    EventBus eventBus() {
        return mock(EventBus.class);
    }

    @Provides
    @Singleton
    MainPresenter mainPresenter() {
        return mock(MainPresenter.class);
    }
}
