package com.levin.sjf4j.core.exclusion;

import com.jn.langx.util.reflect.FieldAttributes;
import com.jn.langx.util.reflect.MethodAttributes;

public class UnderlineStartedExclusion implements Exclusion {
    @Override
    public boolean shouldSkipMethod(MethodAttributes m, boolean serialize) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes, boolean serialize) {
        String fieldName = fieldAttributes.getName();
        return fieldName.startsWith("_");
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass, boolean serialize) {
        return false;
    }
}
