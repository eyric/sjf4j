package com.levin.sjf4j.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.levin.sjf4j.gson.GsonJSONBuilder;
import com.jn.langx.util.Preconditions;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {

    private GsonJSONBuilder jsonBuilder;

    public EnumTypeAdapterFactory(GsonJSONBuilder jsonBuilder) {
        Preconditions.checkNotNull(jsonBuilder);
        this.jsonBuilder = jsonBuilder;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType().isEnum()) {
            EnumTypeAdapter enumTypeAdapter = new EnumTypeAdapter();
            enumTypeAdapter.setUsingValue(jsonBuilder.serializeEnumUsingIndex());
            enumTypeAdapter.setUsingField(jsonBuilder.serializeEnumUsingField());
            enumTypeAdapter.setUsingToString(jsonBuilder.serializeEnumUsingToString());
            enumTypeAdapter.setJSONBuilder(jsonBuilder);
            enumTypeAdapter.setEnumClass(type.getRawType());
            return (TypeAdapter<T>) enumTypeAdapter;
        }
        return null;
    }
}
