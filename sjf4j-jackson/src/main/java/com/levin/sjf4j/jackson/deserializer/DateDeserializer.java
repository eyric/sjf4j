package com.levin.sjf4j.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.levin.sjf4j.core.codec.dialect.PropertyCodecConfiguration;
import com.levin.sjf4j.jackson.JacksonConstants;
import com.levin.sjf4j.jackson.Jacksons;
import com.jn.langx.util.Dates;
import com.jn.langx.util.Strings;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static com.levin.sjf4j.jackson.JacksonConstants.ENABLE_CUSTOM_CONFIGURATION;

public class DateDeserializer<T extends Date> extends JsonDeserializer<T> implements ContextualDeserializer {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
        JsonToken curr = p.getCurrentToken();
        if (curr == JsonToken.VALUE_NULL) {
            return null;
        }

        DateFormat df = null;
        String pattern = null;
        if (Jacksons.getBooleanAttr(ctx, ENABLE_CUSTOM_CONFIGURATION)) {
            PropertyCodecConfiguration propertyCodecConfiguration = Jacksons.getPropertyCodecConfiguration(p);
            if (propertyCodecConfiguration != null) {
                df = propertyCodecConfiguration.getDateFormat();
                pattern = propertyCodecConfiguration.getDatePattern();

                if (df == null && Strings.isNotBlank(pattern)) {
                    df = Dates.getSimpleDateFormat(pattern);
                }
            }
        }
        if (df == null) {
            df = Jacksons.getDateFormatAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_DATE_FORMAT_ATTR_KEY);
        }
        if (pattern == null) {
            pattern = Jacksons.getStringAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_PATTERN_ATTR_KEY);
        }
        boolean usingToString = Jacksons.getBooleanAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY);


        if (curr == JsonToken.VALUE_STRING) {
            if (df == null && Strings.isNotBlank(pattern)) {
                df = Dates.getSimpleDateFormat(pattern);
            }
            if (df != null) {
                try {
                    return (T) df.parse(p.getValueAsString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (usingToString) {
                return (T) new Date(p.getValueAsString());
            } else {
                return null;
            }
        }
        if (curr.isNumeric()) {
            long timestamp = p.getLongValue();
            return (T) new Date(timestamp);
        }
        return null;
    }

    @Override
    public JsonDeserializer<T> createContextual(DeserializationContext context, BeanProperty beanProperty, Class<?> type) throws JsonMappingException {
        if (type == Date.class) {
            return this;
        }
        return null;
    }
}
