package com.levin.sjf4j.fastjson.ext;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class NullPropertyFilter implements PropertyFilter {

    private boolean serializeNulls;

    public NullPropertyFilter(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }

    @Override
    public boolean apply(Object object, String name, Object value) {
        if (serializeNulls) {
            return true;
        }
        if (value == null) {
            return false;
        }
        return true;
    }
}
