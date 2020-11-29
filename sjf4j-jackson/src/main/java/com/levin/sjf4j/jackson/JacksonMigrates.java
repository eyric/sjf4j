package com.levin.sjf4j.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JacksonMigrates {
    /**
     * 迁移自 ClassUtil ，从高版本迁移
     */
    public static String exceptionMessage(Throwable t) {
        if (t instanceof JsonProcessingException) {
            return ((JsonProcessingException) t).getOriginalMessage();
        }
        return t.getMessage();
    }

    /**
     * 迁移自 ClassUtil ，从高版本迁移
     */
    public static void verifyMustOverride(Class<?> expType, Object instance,
                                          String method)
    {
        if (instance.getClass() != expType) {
            throw new IllegalStateException(String.format(
                    "Sub-class %s (of class %s) must override method '%s'",
                    instance.getClass().getName(), expType.getName(), method));
        }
    }
}
