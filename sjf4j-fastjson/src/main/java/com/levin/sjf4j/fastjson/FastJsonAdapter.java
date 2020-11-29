package com.levin.sjf4j.fastjson;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.levin.sjf4j.core.JsonException;
import com.levin.sjf4j.core.JsonHandler;
import com.levin.sjf4j.core.JsonTreeNode;
import com.levin.sjf4j.core.tree.JsonTreeDeserializer;

import java.io.Reader;
import java.lang.reflect.Type;

public class FastJsonAdapter implements JsonHandler {
    private FastJson fastJson;

    @Override
    public <T> T deserialize(String json, Type typeOfT) throws JsonException {
        DefaultJSONParser parser = fastJson.getDeserializerBuilder().build(json);
        T value = parser.parseObject(typeOfT);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JsonException {
        JSONReaderScanner jsonReaderScanner = new JSONReaderScanner(reader, fastJson.getDeserializerBuilder().getFeatures());
        JSONReader jsonReader = new JSONReader(jsonReaderScanner);
        return jsonReader.readObject(typeOfT);
    }

    @Override
    public JsonTreeNode deserialize(String json) throws JsonException {
        return new JsonTreeDeserializer().parse(json);
    }

    @Override
    public String serialize(Object src, Type typeOfT) throws JsonException {
        if (src instanceof JsonTreeNode) {
            JsonTreeNode element = (JsonTreeNode) src;
            return fastJson.getJsonTreeSerializerBuilder().build().toJson(element);
        }
        JSONSerializer serializer = fastJson.getSerializerBuilder().build();
        serializer.write(src);
        return serializer.toString();
    }

    public void setFastJson(FastJson fastJson) {
        this.fastJson = fastJson;
    }
}
