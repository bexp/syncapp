package com.syncapp;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.syncapp.model.Notification;
import com.syncapp.service.NotificationService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bexp on 4/30/16.
 */


@RunWith(AndroidJUnit4.class)
public class NotificationTest extends BaseTest {

    private static final String TAG = NotificationTest.class.getSimpleName();

    @Inject
    MockWebServer webServer;

    @Inject
    NotificationService service;

    @Inject
    Realm realm;

    private CountDownLatch lock = new CountDownLatch(1);

    @Before
    @Override
    public void setUp() throws Exception  {
        super.setUp();
        getTestComponent().inject(this);
        String body =" \"[\n"+
                " {\n"+
                " \"id\":\"ffee77ef-338a-4857-b6af-544017dded88\",\n"+
                " \"created_at\":\"2016-01-08 20:28:31 -0800\",\n"+
                " \"seen_at\":null,\n"+
                " \"read_at\":\"2016-04-19 16:21:43 -0700\",\n"+
                " \"event_data\":{\n"+
                " \"name\":\"listing.bookmarked\",\n"+
                " \"potential_buyer\":{\n"+
                " \"name\":\"A potential buyer\"\n"+
                " },\n"+
                " \"title\":\"<b>A potential buyer</b> bookmarked your vehicle listing.\\n\",\n"+
                " \"listing\":{\n"+
                " \"id\":\"8b08fe2e-f515-4de3-8907-a24e6659e916\",\n"+
                " \"cover_image_url\":\"https://instamotor.imgix.net/fd1fc180-97a6-4551-bee0-34b5b6baeeb9\"\n"+
                " }\n"+
                " }\n"+
                " }]\"";

        Log.d(TAG, "fetchNotificationsTest prepare");

        webServer.enqueue(new MockResponse().setResponseCode(200).setBody(body));
    }

    @Test
    public void testNotifications() throws Exception {
        RealmResults<Notification> locals = service.getLocalNotifications();
        assertEquals(0, locals.size());

        service.fetchNotificationsAsync();
        lock.await(2000, TimeUnit.MILLISECONDS);
        realm.refresh();

        assertEquals(10, locals.size());
    }
}
