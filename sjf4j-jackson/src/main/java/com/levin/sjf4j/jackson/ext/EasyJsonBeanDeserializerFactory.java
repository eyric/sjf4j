package com.levin.sjf4j.jackson.ext;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.levin.sjf4j.core.exclusion.ExclusionConfiguration;
import com.levin.sjf4j.jackson.JacksonMigrates;
import com.levin.sjf4j.jackson.deserializer.DateDeserializer;
import com.levin.sjf4j.jackson.deserializer.NumberDeserializer;
import com.jn.langx.util.reflect.type.Types;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EasyJsonBeanDeserializerFactory extends BeanDeserializerFactory {
    private EasyJsonObjectMapper objectMapper;

    EasyJsonBeanDeserializerFactory(DeserializerFactoryConfig config, EasyJsonObjectMapper objectMapper) {
        super(config);
        this.objectMapper = objectMapper;
    }

    @Override
    public DeserializerFactory withConfig(DeserializerFactoryConfig config) {
        if (_factoryConfig == config) {
            return this;
        }

        JacksonMigrates.verifyMustOverride(EasyJsonBeanDeserializerFactory.class, this, "withConfig");
        return new EasyJsonBeanDeserializerFactory(config, objectMapper);
    }

    @Override
    protected JsonDeserializer<?> findStdDeserializer(DeserializationContext ctxt,
                                                      JavaType type, BeanDescription beanDesc)
            throws JsonMappingException {
        // note: we do NOT check for custom deserializers here, caller has already
        // done that
        Class<?> rawType = type.getRawClass();
        String clsName = rawType.getName();
        if (Types.isPrimitive(rawType) || clsName.startsWith("java.")) {
            // Primitives/wrappers, other Numbers:
            JsonDeserializer<?> deser = null;
            // code block append by easyjson [start]
            if (Number.class.isAssignableFrom(rawType)) {
                deser = new NumberDeserializer().createContextual(ctxt, null, Types.getPrimitiveWrapClass(rawType));
                if (deser != null) {
                    return deser;
                }
            }
            // code block append by easyjson [end]
        }
        if (Date.class.isAssignableFrom(rawType)) {
            JsonDeserializer<?> deser = new DateDeserializer().createContextual(ctxt, null, rawType);
            if (deser != null) {
                return deser;
            }
        }
        return super.findStdDeserializer(ctxt, type, beanDesc);
    }

    @Override
    protected List<BeanPropertyDefinition> filterBeanProps(DeserializationContext ctxt,
                                                           BeanDescription beanDesc, BeanDeserializerBuilder builder,
                                                           List<BeanPropertyDefinition> propDefsIn,
                                                           Set<String> ignored)
            throws JsonMappingException {
        List<BeanPropertyDefinition> propertyDefinitions = super.filterBeanProps(ctxt, beanDesc, builder, propDefsIn, ignored);
        if (objectMapper != null) {
            ExclusionConfiguration exclusionConfiguration = objectMapper.getJsonBuilder().getExclusionConfiguration();
            Class clazz = beanDesc.getType().getRawClass();
            if (!exclusionConfiguration.isExcludedClass(clazz, false)) {
                Iterator<BeanPropertyDefinition> iter = propertyDefinitions.iterator();
                while (iter.hasNext()) {
                    BeanPropertyDefinition property = iter.next();
                    if (property.hasField()) {
                        Field field = property.getField().getAnnotated();
                        if (exclusionConfiguration.isExcludedField(field, false)) {
                            iter.remove();
                        }
                    }
                }
            } else {
                propertyDefinitions.clear();
            }
        }
        return propertyDefinitions;
    }
}
