package com.levin.sjf4j.core.exclusion;

import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.reflect.FieldAttributes;
import com.jn.langx.util.reflect.MethodAttributes;

import java.util.List;

public class FieldNamesExclusion implements Exclusion {
    /**
     * 排除的字段名
     */
    private List<String> fieldNames = Collects.emptyArrayList();

    public FieldNamesExclusion(List<String> fieldNames) {
        Collects.addAll(this.fieldNames, fieldNames);
    }

    public void addField(String name) {
        this.fieldNames.add(name);
    }

    @Override
    public boolean shouldSkipMethod(MethodAttributes m, boolean serializePhrase) {
        final String method = m.getName();
        return Collects.anyMatch(fieldNames, new Predicate<String>() {
            @Override
            public boolean test(String fieldName) {
                return ("set" + fieldName).equalsIgnoreCase(method)
                        || ("get" + fieldName).equalsIgnoreCase(method)
                        || ("is" + fieldName).equalsIgnoreCase(method);
            }
        });
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f, boolean serializePhrase) {
        return fieldNames.contains(f.getName());
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz, boolean serializePhrase) {
        return false;
    }
}
