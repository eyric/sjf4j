package com.levin.sjf4j.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.levin.sjf4j.jackson.Jacksons;
import com.jn.langx.util.reflect.type.Types;

import java.io.IOException;

import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_LONG_USING_STRING_ATTR_KEY;
import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_NUMBER_USING_STRING_ATTR_KEY;

public class NumberSerializer extends JsonSerializer<Number> implements ContextualSerializer {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        boolean longUsingString = Jacksons.getBoolean(sp.getAttribute(SERIALIZE_LONG_USING_STRING_ATTR_KEY));
        boolean usingString = Jacksons.getBoolean(sp.getAttribute(SERIALIZE_NUMBER_USING_STRING_ATTR_KEY));
        if (longUsingString) {
            Class typeOfSrc = value.getClass();
            if (Long.class == typeOfSrc || long.class == typeOfSrc) {
                gen.writeString(value.toString());
                return;
            } else {
                gen.writeNumber(value.toString());
                return;
            }
        }
        if (usingString) {
            gen.writeString(value.toString());
            return;
        }
        gen.writeNumber(value.toString());
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property, JavaType type) throws JsonMappingException {
        if (type == null) {
            return null;
        }
        Class<?> rawType = type.getRawClass();
        String clsName = rawType.getName();
        if (Types.isPrimitive(rawType) || clsName.startsWith("java.")) {
            if (Jacksons.getBoolean(prov.getAttribute(SERIALIZE_NUMBER_USING_STRING_ATTR_KEY)) || Jacksons.getBoolean(prov.getAttribute(SERIALIZE_LONG_USING_STRING_ATTR_KEY))) {
                return this;
            }
        }
        return null;
    }
}
