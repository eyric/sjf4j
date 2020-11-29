package com.levin.sjf4j.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.levin.sjf4j.jackson.deserializer.BooleanDeserializer;
import com.levin.sjf4j.jackson.deserializer.DateDeserializer;
import com.levin.sjf4j.jackson.deserializer.EnumDeserializer;
import com.levin.sjf4j.jackson.deserializer.NumberDeserializer;
import com.jn.langx.util.reflect.type.Types;

import java.util.Date;

public class EasyjsonBeanDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        Class beanClass = beanDesc.getBeanClass();
        if (Boolean.class == beanClass || boolean.class == beanClass) {
            return new BooleanDeserializer();
        }
        if (Types.isPrimitive(beanClass)) {
            if (Number.class.isAssignableFrom(Types.getPrimitiveWrapClass(beanClass))) {
                return new NumberDeserializer(beanClass);
            }
        }
        if (Date.class.isAssignableFrom(beanClass)) {
            return new DateDeserializer();
        }
        if (beanClass.isEnum()) {
            return new EnumDeserializer(beanClass);
        }
        return super.modifyDeserializer(config, beanDesc, deserializer);
    }
}
