package com.levin.sjf4j.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.levin.sjf4j.jackson.serializer.BooleanSerializer;
import com.levin.sjf4j.jackson.serializer.DateSerializer;
import com.levin.sjf4j.jackson.serializer.EnumSerializer;
import com.levin.sjf4j.jackson.serializer.NumberSerializer;
import com.jn.langx.util.reflect.Reflects;
import com.jn.langx.util.reflect.type.Types;

import java.util.Date;
import java.util.List;

public class EasyjsonBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }

    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        Class beanClass = beanDesc.getBeanClass();
        if(Boolean.class == beanClass || boolean.class==beanClass){
            return new BooleanSerializer();
        }
        if (Reflects.isSubClassOrEquals(Number.class, beanClass) || Types.isPrimitive(beanClass) ) {
            if (Number.class.isAssignableFrom(Types.getPrimitiveWrapClass(beanClass))) {
                return new NumberSerializer();
            }
        }
        if (Date.class.isAssignableFrom(beanClass)) {
             return new DateSerializer();
        }
        if(beanClass.isEnum()){
            return new EnumSerializer();
        }
        return super.modifySerializer(config, beanDesc, serializer);
    }
}
