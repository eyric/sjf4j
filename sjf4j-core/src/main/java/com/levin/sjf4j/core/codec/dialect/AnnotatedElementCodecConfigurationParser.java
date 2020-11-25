package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.configuration.ConfigurationParser;

import java.lang.reflect.AnnotatedElement;

public interface AnnotatedElementCodecConfigurationParser<T extends CodecConfiguration> extends ConfigurationParser<AnnotatedElement, T> {
    @Override
    public T parse(AnnotatedElement annotatedElement);
}
