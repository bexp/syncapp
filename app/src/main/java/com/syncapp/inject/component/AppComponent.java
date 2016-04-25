package com.syncapp.inject.component;

import com.syncapp.App;
import com.syncapp.inject.module.AppModule;
import com.syncapp.service.NotificationService;
import com.syncapp.ui.MainActivity;
import com.syncapp.ui.presenter.MainPresenter;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by bexp on 4/22/16.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);
    void inject(MainActivity activity);
    void inject(NotificationService service);
    void inject(MainPresenter presenter);
}
