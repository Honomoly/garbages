package com.honomoly.garbages.config.serializer;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InstantToDatetimeSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null)
            gen.writeNull();
        else
            gen.writeString(value.toString());
    }

}
