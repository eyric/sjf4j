package com.levin.sjf4j.gson.typeadapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.levin.sjf4j.gson.GsonJSONBuilder;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.reflect.Reflects;

import java.util.Date;

public class DateTypeAdapterFactory implements TypeAdapterFactory {
    private GsonJSONBuilder jsonBuilder;

    public DateTypeAdapterFactory(GsonJSONBuilder jsonBuilder) {
        Preconditions.checkNotNull(jsonBuilder);
        this.jsonBuilder = jsonBuilder;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (Reflects.isSubClassOrEquals(Date.class, type.getRawType())) {
            DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
            dateTypeAdapter.setDateFormat(jsonBuilder.serializeUseDateFormat());
            dateTypeAdapter.setPattern(jsonBuilder.serializeDateUsingPattern());
            dateTypeAdapter.setUsingToString(jsonBuilder.serializeDateUsingToString());
            dateTypeAdapter.setJSONBuilder(jsonBuilder);
            return (TypeAdapter<T>) dateTypeAdapter;
        }
        return null;
    }
}
