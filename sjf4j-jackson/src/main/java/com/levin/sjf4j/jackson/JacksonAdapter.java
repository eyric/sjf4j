package com.levin.sjf4j.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levin.sjf4j.core.JsonException;
import com.levin.sjf4j.core.JsonHandler;
import com.levin.sjf4j.core.JsonTreeNode;
import com.levin.sjf4j.jackson.node.JacksonBasedJsonTreeNodeMapper;
import com.jn.langx.util.reflect.type.Types;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JacksonAdapter implements JsonHandler {
    private ObjectMapper objectMapper;
    private JacksonBasedJsonTreeNodeMapper treeNodeMapper = new JacksonBasedJsonTreeNodeMapper();

    @Override
    public <T> T deserialize(String json, Type typeOfT) throws JsonException {
        try {
            return objectMapper.readValue(json, toJavaType(typeOfT));
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JsonException {
        try {
            return objectMapper.readValue(reader, toJavaType(typeOfT));
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    private JavaType toJavaType(Type typeOfT) {
        if (Jacksons.isJacksonJavaType(typeOfT)) {
            return Jacksons.toJavaType(typeOfT);
        }
        if (Types.isPrimitive(typeOfT)) {
            return objectMapper.getTypeFactory().constructType(Types.getPrimitiveWrapClass(typeOfT));
        }

        if (Types.isClass(typeOfT)) {
            return objectMapper.getTypeFactory().constructType(Types.toClass(typeOfT));
        }

        if (Types.isParameterizedType(typeOfT)) {
            ParameterizedType pType = (ParameterizedType) typeOfT;
            Class<?> parametrized = Types.toClass(pType.getRawType());
            Type[] parameterTypes = pType.getActualTypeArguments();
            JavaType[] parameterClasses = new JavaType[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterClasses[i] = toJavaType(parameterTypes[i]);
            }
            return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        }
        return Jacksons.toJavaType(typeOfT);
    }

    @Override
    public JsonTreeNode deserialize(String json) throws JsonException {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return treeNodeMapper.create(jsonNode);
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    @Override
    public String serialize(Object src, Type typeOfT) throws JsonException {
        try {
            if (src instanceof JsonTreeNode) {
                return objectMapper.writeValueAsString(treeNodeMapper.mapping((JsonTreeNode) src));
            }
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
