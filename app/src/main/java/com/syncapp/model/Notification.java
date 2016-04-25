package com.syncapp.model;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by bexp on 4/21/16.
 */
public class Notification extends RealmObject {
    @Getter @Setter @PrimaryKey
    private String id;
    @Getter @Setter private Date created_at;
    @Getter @Setter private Date seen_at;
    @Getter @Setter private Date read_at;
    @Getter @Setter String event_data;
}
