package com.levin.sjf4j.gson.exclusion;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.levin.sjf4j.core.exclusion.Exclusion;
import com.jn.langx.util.reflect.MethodAttributes;
import com.jn.langx.util.reflect.Reflects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DelegateExclusionStrategy implements ExclusionStrategy {
    private Exclusion exclusion;
    private boolean serialize;

    public DelegateExclusionStrategy(Exclusion exclusion, boolean serialize) {
        this.exclusion = exclusion;
        this.serialize = serialize;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        Field field = Reflects.getAnyFieldValue(f, "field", true, true);
        Method getterOrSetter = serialize ? Reflects.getGetter(field.getDeclaringClass(), field.getName()) : Reflects.getSetter(field.getDeclaringClass(), f.getName());
        boolean skip = false;
        if (getterOrSetter != null) {
            skip = exclusion.shouldSkipMethod(new MethodAttributes(getterOrSetter), serialize);
        }
        if (!skip) {
            skip = exclusion.shouldSkipField(new GsonFieldAttributesAdapter(f, field), serialize);
        }
        return skip;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return exclusion.shouldSkipClass(clazz, serialize);
    }
}
