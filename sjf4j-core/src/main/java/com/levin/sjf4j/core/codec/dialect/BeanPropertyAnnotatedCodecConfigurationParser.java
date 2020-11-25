package com.levin.sjf4j.core.codec.dialect;

import java.lang.reflect.AnnotatedElement;

public interface BeanPropertyAnnotatedCodecConfigurationParser extends AnnotatedElementCodecConfigurationParser<PropertyCodecConfiguration>{
    @Override
    public PropertyCodecConfiguration parse(AnnotatedElement annotatedElement);
}
