package com.levin.sjf4j.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.levin.sjf4j.core.codec.dialect.PropertyCodecConfiguration;
import com.levin.sjf4j.jackson.JacksonConstants;
import com.levin.sjf4j.jackson.Jacksons;
import com.jn.langx.util.Dates;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Strings;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class DateSerializer<T extends Date> extends JsonSerializer<T> implements ContextualSerializer {
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider ctx) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        DateFormat df = null;
        String pattern = null;

        if (Jacksons.getBooleanAttr(ctx, JacksonConstants.ENABLE_CUSTOM_CONFIGURATION)) {
            PropertyCodecConfiguration propertyCodecConfiguration = Jacksons.getPropertyCodecConfiguration(gen);
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
        if (Emptys.isEmpty(pattern)) {
            pattern = Jacksons.getStringAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_PATTERN_ATTR_KEY);
        }
        boolean usingToString = Jacksons.getBooleanAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY);
        if (df == null && Strings.isNotBlank(pattern)) {
            df = Dates.getSimpleDateFormat(pattern);
        }

        if (df != null) {
            gen.writeString(df.format(value));
            return;
        }
        if (usingToString) {
            gen.writeString(value.toString());
        }
        gen.writeNumber(value.getTime());
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property, JavaType type) throws JsonMappingException {
        if (type.getRawClass() == Date.class) {
            return this;
        }
        return null;
    }
}
