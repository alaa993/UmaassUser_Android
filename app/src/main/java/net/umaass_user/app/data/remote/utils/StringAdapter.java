package net.umaass_user.app.data.remote.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringAdapter extends TypeAdapter<String> {

    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.STRING) {
            reader.nextString();
            return null;
        }
        return reader.nextString();
    }

    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
