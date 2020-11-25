package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.util.Preconditions;
import com.jn.langx.util.reflect.Reflects;

public class BeanPropertyIdGenerator extends PropertyIdGenerator {
    private Class beanClazz;
    private String propertyName;

    public BeanPropertyIdGenerator withBeanClass(Class beanClass) {
        this.beanClazz = beanClass;
        return this;
    }

    public BeanPropertyIdGenerator withPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    @Override
    public String get() {
        return get(this.propertyName);
    }

    @Override
    public String get(String propertyName) {
        Preconditions.checkNotNull(beanClazz);
        Preconditions.checkNotNull(propertyName);
        return Reflects.getFQNClassName(beanClazz) + "#" + propertyName;
    }
}
