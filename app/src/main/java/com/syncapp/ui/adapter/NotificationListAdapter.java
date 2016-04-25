package com.syncapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syncapp.R;
import com.syncapp.model.Notification;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by bexp on 4/24/16.
 */
public class NotificationListAdapter extends RecyclerView.Adapter implements RealmChangeListener {
    private RealmResults<Notification> notifications;

    public NotificationListAdapter() {}

    public void setNotifications(RealmResults<Notification> notifications) {
        this.notifications = notifications;
        this.notifications.addChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Notification notification = notifications.get(position);
        ((NotificationViewHolder)holder).created_at.setText(notification.getCreated_at().toString());
        ((NotificationViewHolder)holder).event_data.setText(notification.getEvent_data());
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    @Override
    public void onChange() {
        notifyDataSetChanged();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.created_at) TextView created_at;
        @Bind(R.id.event_data) TextView event_data;

        public NotificationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
