package com.levin.sjf4j.fastjson;

import com.levin.sjf4j.core.tree.JsonTreeSerializerBuilder;

public class FastJson {

    private FastJsonSerializerBuilder serializerBuilder;
    private FastJsonParserBuilder deserializerBuilder;
    private JsonTreeSerializerBuilder jsonTreeSerializerBuilder;

    public FastJson(FastJsonSerializerBuilder serializerBuilder, FastJsonParserBuilder deserializerBuilder, JsonTreeSerializerBuilder jsonTreeSerializerBuilder) {
        this.serializerBuilder = serializerBuilder;
        this.deserializerBuilder = deserializerBuilder;
        this.jsonTreeSerializerBuilder = jsonTreeSerializerBuilder;
    }

    public JsonTreeSerializerBuilder getJsonTreeSerializerBuilder() {
        return jsonTreeSerializerBuilder;
    }

    public FastJsonSerializerBuilder getSerializerBuilder() {
        return serializerBuilder;
    }

    public FastJsonParserBuilder getDeserializerBuilder() {
        return deserializerBuilder;
    }
}
