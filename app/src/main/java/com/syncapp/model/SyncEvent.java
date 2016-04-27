package com.syncapp.model;

import android.support.annotation.NonNull;

import lombok.Getter;

/**
 * Created by bexp on 4/24/16.
 */
public class SyncEvent {
    @Getter private String message;
    public SyncEvent(@NonNull String message) {
        this.message = message;
    }
}
