package com.syncapp;

import android.app.Application;
import com.syncapp.inject.module.AppModule;
import com.syncapp.inject.component.AppComponent;
import com.syncapp.inject.component.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bexp on 4/22/16.
 */
public class App extends Application {
    private AppComponent mComponent;

    @Override public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
