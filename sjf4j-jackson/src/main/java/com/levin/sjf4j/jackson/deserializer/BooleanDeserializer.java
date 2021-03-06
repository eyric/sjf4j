package com.levin.sjf4j.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.levin.sjf4j.jackson.Jacksons;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.levin.sjf4j.jackson.JacksonConstants.SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY;

public class BooleanDeserializer extends JsonDeserializer<Boolean> implements ContextualDeserializer {

    private static final List<String> evalTrues = Arrays.asList(new String[]{"true", "on", "1"});

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
        boolean using1_0 = Jacksons.getBooleanAttr(ctx, SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY);

        JsonToken curr = p.getCurrentToken();
        if (using1_0 && curr == JsonToken.VALUE_NUMBER_INT) {
            return p.getIntValue() == 1;
        }
        if (curr == JsonToken.VALUE_STRING) {
            return evalTrues.contains(p.getValueAsString().toLowerCase());
        }
        if (curr == JsonToken.VALUE_TRUE || curr == JsonToken.VALUE_FALSE) {
            return curr == JsonToken.VALUE_TRUE;
        }
        return false;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty beanProperty, Class<?> type) throws JsonMappingException {
        return null;
    }
}
