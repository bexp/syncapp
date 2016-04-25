package com.syncapp.inject.module;

/**
 * Created by bexp on 4/22/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.syncapp.App;
import com.syncapp.model.Notification;
import com.syncapp.service.ApiService;
import com.syncapp.service.NotificationAdapter;
import com.syncapp.service.NotificationService;
import com.syncapp.ui.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;

@Module
public class AppModule {
    private App app;
    public AppModule(App app) {
        this.app = app;
    }
    private static final String API_ENDPOINT = "http://api.instamotorlabs.com";

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    public App application() {
        return app;
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    public ApiService apiService() {
        OkHttpClient client = new OkHttpClient();

        final GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Notification.class, new NotificationAdapter());

        final Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
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
    MainPresenter mainPresenter() {
        return new MainPresenter(app.getComponent());
    }

    @Provides
    @Singleton
    EventBus eventBus() {
        return EventBus.getDefault();
    }
}
