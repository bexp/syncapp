package com.syncapp.inject.component;

import com.syncapp.App;
import com.syncapp.NotificationTest;
import com.syncapp.inject.module.TestModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by bexp on 4/29/16.
 */

@Singleton
@Component(modules = {TestModule.class})
public interface TestComponent extends AppComponent {
    void inject(App app);
    void inject(NotificationTest test);
}
