package com.levin.sjf4j.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.levin.sjf4j.core.JSONBuilderAware;
import com.levin.sjf4j.gson.GsonJSONBuilder;
import com.jn.langx.util.Preconditions;

public class BooleanTypeAdapterFactory implements TypeAdapterFactory, JSONBuilderAware<GsonJSONBuilder> {
    private GsonJSONBuilder jsonBuilder;

    public BooleanTypeAdapterFactory() {
    }

    public BooleanTypeAdapterFactory(GsonJSONBuilder jsonBuilder) {
        Preconditions.checkNotNull(jsonBuilder);
        this.jsonBuilder = jsonBuilder;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType() == boolean.class || type.getRawType() == Boolean.class) {
            BooleanTypeAdapter booleanTypeAdapter = new BooleanTypeAdapter();
            booleanTypeAdapter.setUsing1_0(jsonBuilder.serializeBooleanUsing1_0());
            booleanTypeAdapter.setUsingOnOff(jsonBuilder.serializeBooleanUsingOnOff());
            booleanTypeAdapter.setJSONBuilder(jsonBuilder);
            return (TypeAdapter<T>) booleanTypeAdapter;
        }
        return null;
    }

    public void setJSONBuilder(GsonJSONBuilder jsonBuilder) {
        this.jsonBuilder = jsonBuilder;
    }

    public GsonJSONBuilder getJSONBuilder() {
        return this.jsonBuilder;
    }
}
