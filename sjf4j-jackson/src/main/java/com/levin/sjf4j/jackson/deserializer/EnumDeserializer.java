package com.levin.sjf4j.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.levin.sjf4j.core.codec.dialect.PropertyCodecConfiguration;
import com.levin.sjf4j.jackson.Jacksons;
import com.jn.langx.util.Emptys;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.EnumSet;

import static com.levin.sjf4j.jackson.JacksonConstants.ENABLE_CUSTOM_CONFIGURATION;
import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY;
import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_ENUM_USING_INDEX_ATTR_KEY;

public class EnumDeserializer<T extends Enum> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> clazz;

    @Override
    public Class<T> handledType() {
        return clazz;
    }

    public EnumDeserializer() {
    }

    public EnumDeserializer(Class clazz) {
        this.clazz = clazz;
    }


    @Override
    public T deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {

        Boolean usingIndex = null;
        Boolean usingToString = null;
        String usingField = null;
        if (Jacksons.getBooleanAttr(ctx, ENABLE_CUSTOM_CONFIGURATION)) {
            PropertyCodecConfiguration propertyCodecConfiguration = Jacksons.getPropertyCodecConfiguration(p);
            if (propertyCodecConfiguration != null) {
                usingIndex = propertyCodecConfiguration.getEnumUsingIndex();
                usingToString = propertyCodecConfiguration.getEnumUsingToString();
            }
        }
        if (usingIndex == null && usingToString == null) {
            usingIndex = Jacksons.getBooleanAttr(ctx, SERIALIZE_ENUM_USING_INDEX_ATTR_KEY);
        }
        if (usingToString == null) {
            usingToString = ctx.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        }
        if (Emptys.isEmpty(usingField)) {
            usingField = (String) ctx.getAttribute(SERIALIZE_ENUM_USING_FIELD_ATTR_KEY);
        }
        Class<T> enumClass = clazz;
        if (enumClass == null) {
            Object currentOwner = p.getCurrentValue();
            String currentName = p.getCurrentName();

            // enum is bean's field
            if (currentName != null && currentOwner != null) {
                try {
                    Field field = currentOwner.getClass().getDeclaredField(currentName);
                    enumClass = (Class<T>) field.getType();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }

        EnumSet es = EnumSet.allOf(enumClass);

        JsonToken jtoken = p.getCurrentToken();

        if (usingIndex && jtoken == JsonToken.VALUE_NUMBER_INT) {
            int index = p.getIntValue();
            for (Object obj : es) {
                T e = (T) obj;
                if (e.ordinal() == index) {
                    return e;
                }
            }
        }

        if (usingToString && jtoken == JsonToken.VALUE_STRING) {
            String string = p.getValueAsString();
            for (Object obj : es) {
                T e = (T) obj;
                if (e.toString().equals(string)) {
                    return e;
                }
            }
        }

        if (usingField != null) {
            try {
                Field field = enumClass.getDeclaredField(usingField);
                Class fieldType = field.getType();
                if (String.class == fieldType && jtoken == JsonToken.VALUE_STRING) {
                    String str = p.getValueAsString();
                    for (Object obj : es) {
                        T e = (T) obj;
                        String v = (String) field.get(e);
                        if (v.equals(str)) {
                            return e;
                        }
                    }
                }
                if (Character.class == fieldType) {
                    char ch = p.getTextCharacters()[0];
                    for (Object obj : es) {
                        T e = (T) obj;
                        Character v = (Character) field.get(e);
                        if (v.equals(ch)) {
                            return e;
                        }
                    }
                }
                if (Boolean.class == fieldType) {
                    boolean bool = p.getBooleanValue();
                    for (Object obj : es) {
                        T e = (T) obj;
                        Boolean v = (Boolean) field.get(e);
                        if (v.equals(bool)) {
                            return e;
                        }
                    }
                }
                if (Number.class == fieldType) {
                    Number number = p.getNumberValue();
                    for (Object obj : es) {
                        T e = (T) obj;
                        Number v = (Number) field.get(e);
                        if (v.equals(number)) {
                            return e;
                        }
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        if (jtoken == JsonToken.VALUE_STRING) {
            String enumName = p.getValueAsString();
            for (Object obj : es) {
                T e = (T) obj;
                if (e.name().equals(enumName)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty beanProperty, Class<?> type) throws JsonMappingException {
        if (handledType() == null || (type != null && handledType() != type)) {
            EnumDeserializer enumDeserializer = new EnumDeserializer();
            enumDeserializer.clazz = type;
            return enumDeserializer;
        }
        return this;
    }
}
