package com.levin.sjf4j.jackson.serializer;

import com.fasterxml.jackson.databind.*;

public interface ContextualSerializer {
    JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property, JavaType type)
            throws JsonMappingException;
}
