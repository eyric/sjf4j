package com.levin.sjf4j.core.factory;

import com.levin.sjf4j.core.JSON;
import com.levin.sjf4j.core.JSONBuilder;
import com.levin.sjf4j.core.JSONFactory;

public class PrototypeJSONFactory implements JSONFactory {
    private JSONBuilder jsonBuilder;

    public PrototypeJSONFactory(){

    }

    public PrototypeJSONFactory(JSONBuilder jsonBuilder){
        setJsonBuilder(jsonBuilder);
    }

    public void setJsonBuilder(JSONBuilder jsonBuilder) {
        this.jsonBuilder = jsonBuilder;
    }

    @Override
    public JSON get() {
        return jsonBuilder.build();
    }
}
