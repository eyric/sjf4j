package com.levin.sjf4j.fastjson.ext;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.util.JavaBeanInfo;

import java.lang.reflect.Type;

public class EasyJsonJavaBeanDeserializer extends JavaBeanDeserializer {
    public EasyJsonJavaBeanDeserializer(ParserConfig config, Class<?> clazz) {
        this(config, clazz, clazz);
    }

    public EasyJsonJavaBeanDeserializer(ParserConfig config, Class<?> clazz, Type type) {
        super(config, clazz, type);
    }

    public EasyJsonJavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo) {
        super(config, config instanceof EasyJsonParserConfig ? ((EasyJsonParserConfig) config).filterFields(beanInfo) : beanInfo);
    }
}
