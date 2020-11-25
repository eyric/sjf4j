package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.IdGenerator;

/**
 * @inheritDoc
 */
public class PropertyIdGenerator implements IdGenerator<String> {
    @Override
    public String get(String propertyName) {
        return propertyName;
    }

    @Override
    public String get() {
        return null;
    }
}
