package com.levin.sjf4j.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.levin.sjf4j.jackson.Jacksons;

import java.io.IOException;

import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY;
import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_BOOLEAN_USING_ON_OFF_ATTR_KEY;

public class BooleanSerializer extends JsonSerializer<Boolean> {

    @Override
    public void serialize(Boolean value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        boolean using1_0 = Jacksons.getBoolean(sp.getAttribute(SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY));
        boolean usingOnOff = Jacksons.getBoolean(sp.getAttribute(SERIALIZE_BOOLEAN_USING_ON_OFF_ATTR_KEY));

        if (usingOnOff) {
            gen.writeString(value ? "on" : "off");
            return;
        }
        if (using1_0) {
            gen.writeNumber(value ? 1 : 0);
            return;
        }
        gen.writeBoolean(value);
    }
}
