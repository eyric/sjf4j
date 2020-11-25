package com.levin.sjf4j.core.exclusion;

import com.jn.langx.util.reflect.FieldAttributes;
import com.jn.langx.util.reflect.MethodAttributes;

public interface Exclusion {

    boolean shouldSkipMethod(MethodAttributes m, boolean serializePhrase);

    /**
     * @param f the field object that is under test
     * @return true if the field should be ignored; otherwise false
     */
    boolean shouldSkipField(FieldAttributes f, boolean serializePhrase);

    /**
     * @param clazz the class object that is under test
     * @return true if the class should be ignored; otherwise false
     */
    boolean shouldSkipClass(Class<?> clazz, boolean serializePhrase);
}
