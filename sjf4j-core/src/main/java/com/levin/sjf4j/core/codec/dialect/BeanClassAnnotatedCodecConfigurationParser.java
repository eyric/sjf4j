package com.levin.sjf4j.core.codec.dialect;

import java.lang.reflect.AnnotatedElement;

public interface BeanClassAnnotatedCodecConfigurationParser extends AnnotatedElementCodecConfigurationParser<ClassCodecConfiguration> {
    @Override
    ClassCodecConfiguration parse(AnnotatedElement annotatedElement);
}
