package com.syncapp.model;

import lombok.Getter;

/**
 * Created by bexp on 4/24/16.
 */
public class SyncEvent {
    @Getter private String message;
    public SyncEvent(String message) {
        this.message = message;
    }
}
