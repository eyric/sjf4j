package com.levin.sjf4j.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.levin.sjf4j.gson.GsonJSONBuilder;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.reflect.Reflects;

public class NumberTypeAdapterFactory implements TypeAdapterFactory {

    private GsonJSONBuilder jsonBuilder;

    public NumberTypeAdapterFactory(GsonJSONBuilder jsonBuilder) {
        Preconditions.checkNotNull(jsonBuilder);
        this.jsonBuilder = jsonBuilder;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (Reflects.isSubClass(Number.class, type.getRawType())) {
            NumberTypeAdapter numberTypeAdapter = new NumberTypeAdapter();
            numberTypeAdapter.setLongUsingString(jsonBuilder.serializeLongAsString());
            numberTypeAdapter.setUsingString(jsonBuilder.serializeNumberAsString());
            numberTypeAdapter.setJSONBuilder(jsonBuilder);
            numberTypeAdapter.setTargetClass(type.getRawType());
            return (TypeAdapter<T>) numberTypeAdapter;
        }
        return null;
    }
}
