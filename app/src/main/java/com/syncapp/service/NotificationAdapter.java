package com.syncapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.syncapp.model.Notification;

import java.io.IOException;
import java.util.Date;

/**
 * Created by bexp on 4/19/16.
 */
public class NotificationAdapter extends TypeAdapter<Notification> {
    final GsonBuilder builder = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss Z");
    final Gson gson = builder.create();

    public Notification read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        Notification notification = new Notification();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                notification.setId(reader.nextString());
            } else if (name.equals("created_at")) {
                notification.setCreated_at((Date)gson.fromJson(reader, Date.class));
            } else if (name.equals("event_data")) {
                JsonElement element = gson.fromJson(reader, JsonElement.class);
                notification.setEvent_data(element.toString());
            } else if (name.equals("read_at")){
                notification.setRead_at((Date)gson.fromJson(reader, Date.class));
            } else if (name.equals("seen_at")) {
                notification.setSeen_at((Date)gson.fromJson(reader, Date.class));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return notification;
    }

    public void write(JsonWriter writer, Notification value) throws IOException {
        //don't need it for now
        throw new UnsupportedOperationException();
    }
}
