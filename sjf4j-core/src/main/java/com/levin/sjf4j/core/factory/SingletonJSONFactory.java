package com.levin.sjf4j.core.factory;

import com.levin.sjf4j.core.JSON;
import com.levin.sjf4j.core.JSONBuilder;
import com.levin.sjf4j.core.JSONFactory;

public class SingletonJSONFactory implements JSONFactory {
    private JSONBuilder jsonBuilder;
    private JSON json;

    public SingletonJSONFactory() {
    }

    public SingletonJSONFactory(JSONBuilder jsonBuilder) {
        setJsonBuilder(jsonBuilder);
    }

    public void setJsonBuilder(JSONBuilder jsonBuilder) {
        this.jsonBuilder = jsonBuilder;
    }

    @Override
    public JSON get() {
        if (json != null) {
            return json;
        } else {
            synchronized (this) {
                if (json == null) {
                    json = this.jsonBuilder.build();
                }
            }
        }
        return json;
    }
}
