package com.syncapp;

/**
 * Created by bexp on 4/29/16.
 */

import android.test.AndroidTestCase;
import com.syncapp.inject.component.TestComponent;
import com.syncapp.inject.component.DaggerTestComponent;
import com.syncapp.inject.module.TestModule;

import org.junit.Before;
import org.junit.Test;
import android.support.test.InstrumentationRegistry;

public class BaseTest extends AndroidTestCase {
    private TestComponent mTestComponent;
    protected TestComponent getTestComponent() {
        return mTestComponent;
    }

    @Before
    public final void setupBaseTest() throws Exception {
        super.setUp();
        App app = (App) InstrumentationRegistry.getTargetContext().getApplicationContext();
        mTestComponent = DaggerTestComponent.builder()
                 .testModule(new TestModule(app))
                 .build();
    }


    @Test
    public void setupCheck() throws Exception {
        assertNotNull(mTestComponent);
    }
}
