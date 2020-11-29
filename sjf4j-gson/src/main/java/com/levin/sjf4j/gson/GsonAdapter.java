package com.levin.sjf4j.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.levin.sjf4j.core.JsonException;
import com.levin.sjf4j.core.JsonHandler;
import com.levin.sjf4j.core.JsonTreeNode;
import com.levin.sjf4j.gson.node.GsonBasedJsonTreeNodeMapper;

import java.io.Reader;
import java.lang.reflect.Type;

public class GsonAdapter implements JsonHandler {
    private GsonBasedJsonTreeNodeMapper mapper = new GsonBasedJsonTreeNodeMapper();
    private Gson gson;

    @Override
    public String serialize(Object src, Type typeOfT) throws JsonException {
        if (src instanceof JsonTreeNode) {
            JsonElement element = mapper.mapping((JsonTreeNode) src);
            return gson.toJson(element, element.getClass());
        }
        return gson.toJson(src, typeOfT);
    }

    @Override
    public <T> T deserialize(String json, Type typeOfT) throws JsonException {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JsonException {
        return gson.fromJson(reader, typeOfT);
    }

    @Override
    public JsonTreeNode deserialize(String json) throws JsonException {
        JsonElement node = new JsonParser().parse(json);
        return mapper.create(node);
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
