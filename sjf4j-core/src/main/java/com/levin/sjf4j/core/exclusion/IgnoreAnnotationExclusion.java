package com.levin.sjf4j.core.exclusion;

import com.levin.sjf4j.core.annotation.Ignore;
import com.jn.langx.util.reflect.FieldAttributes;
import com.jn.langx.util.reflect.MethodAttributes;

public class IgnoreAnnotationExclusion implements Exclusion {

    @Override
    public boolean shouldSkipMethod(MethodAttributes m, boolean serialize) {
        Ignore ignore =  m.getAnnotation(Ignore.class);
        if (ignore == null) {
            return false;
        }
        return (serialize && ignore.write()) || (!serialize && ignore.read());
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f, boolean serialize) {
        Ignore ignore = f.getAnnotation(Ignore.class);
        if (ignore == null) {
            return false;
        }
        return (serialize && ignore.write()) || (!serialize && ignore.read());
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz, boolean serialize) {
        return false;
    }
}
