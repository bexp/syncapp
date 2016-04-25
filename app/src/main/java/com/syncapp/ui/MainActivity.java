package com.syncapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import com.syncapp.App;
import com.syncapp.R;
import com.syncapp.model.Notification;
import com.syncapp.ui.adapter.NotificationListAdapter;
import com.syncapp.ui.presenter.MainPresenter;
import com.syncapp.ui.view.MainView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {
    @Bind(R.id.view_container) LinearLayout linearLayout;
    @Bind(R.id.recycler_view) RecyclerView items;

    @Inject
    MainPresenter presenter;
    private NotificationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication())
                .getComponent()
                .inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();
        presenter.setView(this);

        Snackbar snackbar = Snackbar
                .make(linearLayout, getString(R.string.welcome), Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void showNotifications(RealmResults<Notification> notifications) {
        adapter.setNotifications(notifications);
    }

    @Override
    public void showSyncMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(linearLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        items.setHasFixedSize(true);
        items.setLayoutManager(linearLayoutManager);
        adapter = new NotificationListAdapter();
        items.setAdapter(adapter);
    }
}
