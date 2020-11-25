package com.levin.sjf4j.core.tree;

public class JsonTreeSerializerBuilder {
    private boolean serializeNulls = false;
    private boolean prettyFormat = false;

    public JsonTreeSerializerBuilder setSerializeNulls(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
        return this;
    }

    public JsonTreeSerializerBuilder setPrettyFormat(boolean prettyFormat) {
        this.prettyFormat = prettyFormat;
        return this;
    }

    public JsonTreeSerializer build() {
        JsonTreeSerializer writer = new JsonTreeSerializer();
        writer.setPrettyFormat(prettyFormat);
        writer.setSerializeNulls(serializeNulls);
        return writer;
    }
}
