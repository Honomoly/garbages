package com.honomoly.garbages.config.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ByteToIntSerializer extends JsonSerializer<Byte> {

    @Override
    public void serialize(Byte value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null)
            gen.writeNull();
        else
            gen.writeNumber(value.intValue());
    }

}
