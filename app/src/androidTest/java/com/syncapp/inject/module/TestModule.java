package com.syncapp.inject.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.syncapp.App;
import com.syncapp.model.Notification;
import com.syncapp.service.ApiService;
import com.syncapp.service.NotificationAdapter;
import com.syncapp.service.NotificationService;
import com.syncapp.ui.presenter.MainPresenter;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static org.mockito.Mockito.mock;

/**
 * Created by bexp on 4/29/16.
 */

@Module
public class TestModule {
    private App app;
    private MockWebServer mockWebServer;

    public TestModule(App app) throws IOException {
        this.app = app;
        this.mockWebServer = new MockWebServer();
        initRealmConfiguration();
        this.mockWebServer.start();
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
    MockWebServer mockWebServer(){
        return mockWebServer;
    }

    @Provides
    @Singleton
    public ApiService apiService() {
        OkHttpClient client = new OkHttpClient();

        final GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Notification.class, new NotificationAdapter());

        final Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        ApiService service = retrofit.create(ApiService.class);
        return  service;
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

    private void initRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(app)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
