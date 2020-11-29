package com.levin.sjf4j.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.levin.sjf4j.core.codec.dialect.PropertyCodecConfiguration;
import com.levin.sjf4j.jackson.Jacksons;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.levin.sjf4j.jackson.JacksonConstants.ENABLE_CUSTOM_CONFIGURATION;
import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY;

public class EnumSerializer<T extends Enum> extends JsonSerializer<T> {

    @Override
    public void serialize(T e, JsonGenerator gen, SerializerProvider sp) throws IOException {
        if (e == null) {
            gen.writeNull();
            return;
        }
        Boolean usingIndex = null;
        Boolean usingToString = null;
        if (Jacksons.getBooleanAttr(sp, ENABLE_CUSTOM_CONFIGURATION)) {
            PropertyCodecConfiguration propertyCodecConfiguration = Jacksons.getPropertyCodecConfiguration(gen);
            if (propertyCodecConfiguration != null) {
                usingIndex = propertyCodecConfiguration.getEnumUsingIndex();
                usingToString = propertyCodecConfiguration.getEnumUsingToString();
            }
        }
        if (usingIndex == null && usingToString==null) {
            usingIndex = sp.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        }
        if (usingToString == null) {
            usingToString = sp.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        }
        String usingField = (String) sp.getAttribute(SERIALIZE_ENUM_USING_FIELD_ATTR_KEY);

        if (usingIndex) {
            gen.writeNumber(e.ordinal());
            return;
        }

        if (usingToString) {
            gen.writeString(e.toString());
            return;
        }

        if (usingField != null && !usingField.trim().isEmpty()) {
            usingField = usingField.trim();
            try {
                Field field = e.getClass().getDeclaredField(usingField);
                field.setAccessible(true);
                Class fieldClazz = field.getType();
                if (fieldClazz.isArray() || fieldClazz.isAnonymousClass() || fieldClazz.isAnnotation() || fieldClazz.isSynthetic()) {
                    throw new UnsupportedOperationException();
                }
                if (fieldClazz == String.class) {
                    gen.writeString(field.get(e).toString());
                    return;
                }
                if (fieldClazz == Character.class) {
                    gen.writeRaw((Character) field.get(e));
                    return;
                }
                if (fieldClazz == Boolean.class) {
                    gen.writeBoolean((Boolean) field.get(e));
                    return;
                }
                if (Number.class.isAssignableFrom(fieldClazz)) {
                    gen.writeNumber(((Number) field.get(e)).toString());
                    return;
                }
            } catch (NoSuchFieldException e1) {
                // ignore it
            } catch (IllegalAccessException e1) {
                // ignore it
            }
        }
        gen.writeString(e.name());
    }

}
