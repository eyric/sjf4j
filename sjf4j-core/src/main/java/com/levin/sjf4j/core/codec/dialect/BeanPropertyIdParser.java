package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.Parser;
import com.jn.langx.util.Strings;

public class BeanPropertyIdParser implements Parser<String, BeanPropertyId> {
    @Override
    public BeanPropertyId parse(String qualifiedId) {
        String[] segments = Strings.split(qualifiedId, "#");
        if (segments.length == 0) {
            return null;
        }
        BeanPropertyId propertyId = new BeanPropertyId();
        propertyId.setBeanClass(segments[0]);
        if (segments.length > 1) {
            propertyId.setPropertyName(segments[1]);
        }
        return propertyId;
    }
}
