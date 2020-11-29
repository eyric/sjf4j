package com.levin.sjf4j.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.levin.sjf4j.core.JSON;
import com.levin.sjf4j.core.JSONBuilder;
import com.levin.sjf4j.core.annotation.DependOn;
import com.levin.sjf4j.core.exclusion.ExclusionConfiguration;
import com.levin.sjf4j.jackson.deserializer.BooleanDeserializer;
import com.levin.sjf4j.jackson.deserializer.Deserializers;
import com.levin.sjf4j.jackson.deserializer.EnumDeserializer;
import com.levin.sjf4j.jackson.ext.EasyJsonJacksonAnnotationIntrospector;
import com.levin.sjf4j.jackson.ext.EasyJsonObjectMapper;
import com.levin.sjf4j.jackson.modifier.EasyjsonBeanDeserializerModifier;
import com.levin.sjf4j.jackson.modifier.EasyjsonBeanSerializerModifier;
import com.levin.sjf4j.jackson.serializer.BooleanSerializer;
import com.levin.sjf4j.jackson.serializer.EnumSerializer;
import com.jn.langx.annotation.Name;

@Name("jackson")
@DependOn("com.fasterxml.jackson.databind.ObjectMapper")
public class JacksonJSONBuilder extends JSONBuilder {
    private static boolean moduleInited = false;
    private static SimpleModule module = new SimpleModule();

    static {
        makesureEasyJsonBaseModuleRegisted();
    }

    public JacksonJSONBuilder() {
        super();
        dialectIdentify(Jacksons.JACKSON);
    }

    public JacksonJSONBuilder(ExclusionConfiguration exclusionConfiguration) {
        super(exclusionConfiguration);
        dialectIdentify(Jacksons.JACKSON);
    }

    private static void makesureEasyJsonBaseModuleRegisted() {
        if (!moduleInited) {
            synchronized (JacksonJSONBuilder.class) {
                if (!moduleInited) {
                    SimpleDeserializers simpleDeserializers = new Deserializers();
                    module.setDeserializers(simpleDeserializers);

                    // boolean
                    module.addSerializer(Boolean.class, new BooleanSerializer());
                    module.addDeserializer(Boolean.class, new BooleanDeserializer());

                    // enum
                    module.addDeserializer(Enum.class, new EnumDeserializer<Enum>());
                    module.addSerializer(Enum.class, new EnumSerializer<Enum>());

                    module.setSerializerModifier(new EasyjsonBeanSerializerModifier());
                    module.setDeserializerModifier(new EasyjsonBeanDeserializerModifier());

                    moduleInited = true;
                }
            }
        }
    }

    private void configEnum(EasyJsonObjectMapper objectMapper) {
        // Enum: jackson default priority: ordinal() > toString() > name()
        // Our EnumSerializer priority: ordinal() > toString() > field > name()

        // step 1 : clear old config:
        SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
        serializationConfig = serializationConfig.withoutAttribute(JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY);

        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.withoutAttribute(JacksonConstants.SERIALIZE_ENUM_USING_INDEX_ATTR_KEY);
        deserializationConfig = deserializationConfig.withoutAttribute(JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY);

        // ordinal()
        if (serializeEnumUsingIndex()) {
            serializationConfig = serializationConfig.with(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        }
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_ENUM_USING_INDEX_ATTR_KEY, serializeEnumUsingIndex());
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_ENUM_USING_INDEX_ATTR_KEY, serializeEnumUsingIndex());


        // toString()
        if (serializeEnumUsingToString()) {
            serializationConfig = serializationConfig.with(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            deserializationConfig = deserializationConfig.with(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        }

        // field
        if (serializeEnumUsingField() != null) {
            serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY, serializeEnumUsingField());
            deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_ENUM_USING_FIELD_ATTR_KEY, serializeEnumUsingField());
        }
        objectMapper.setDescrializationConfig(deserializationConfig);
        objectMapper.setSerializationConfig(serializationConfig);
    }

    private void configBoolean(EasyJsonObjectMapper objectMapper) {
        SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY, serializeBooleanUsing1_0());
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_BOOLEAN_USING_ON_OFF_ATTR_KEY, serializeBooleanUsingOnOff());

        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY, serializeBooleanUsing1_0());
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_BOOLEAN_USING_ON_OFF_ATTR_KEY, serializeBooleanUsingOnOff());

        objectMapper.setSerializationConfig(serializationConfig);
        objectMapper.setDescrializationConfig(deserializationConfig);
    }

    private void configDate(EasyJsonObjectMapper objectMapper) {
        SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_DATE_FORMAT_ATTR_KEY, serializeUseDateFormat());
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_PATTERN_ATTR_KEY, serializeDateUsingPattern());
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY, serializeDateUsingToString());

        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_DATE_FORMAT_ATTR_KEY, serializeUseDateFormat());
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_PATTERN_ATTR_KEY, serializeDateUsingPattern());
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY, serializeDateUsingToString());


        objectMapper.setSerializationConfig(serializationConfig);
        objectMapper.setDescrializationConfig(deserializationConfig);
    }

    private void configNumber(EasyJsonObjectMapper objectMapper) {
        SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_LONG_USING_STRING_ATTR_KEY, serializeLongAsString());
        serializationConfig = serializationConfig.withAttribute(JacksonConstants.SERIALIZE_NUMBER_USING_STRING_ATTR_KEY, serializeNumberAsString());

        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_LONG_USING_STRING_ATTR_KEY, serializeLongAsString());
        deserializationConfig = deserializationConfig.withAttribute(JacksonConstants.SERIALIZE_NUMBER_USING_STRING_ATTR_KEY, serializeNumberAsString());

        objectMapper.setSerializationConfig(serializationConfig);
        objectMapper.setDescrializationConfig(deserializationConfig);
    }

    private void configNulls(EasyJsonObjectMapper objectMapper) {
        if (serializeNulls()) {
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
            objectMapper.setConfig(objectMapper.getSerializationConfig()
                    .withAppendedAnnotationIntrospector(new EasyJsonJacksonAnnotationIntrospector())
                    .withAttribute(JacksonConstants.SERIALIZE_NULLS_ATTR_KEY, true)
            );
        } else {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

    }


    @Override
    public JSON build() {
        makesureEasyJsonBaseModuleRegisted();
        EasyJsonObjectMapper mapper = new EasyJsonObjectMapper();
        mapper.setJsonBuilder(this);
        mapper.registerModule(module);

        // 配置全局属性
        mapper.setSerializationConfig(mapper.getSerializationConfig().withAttribute(JacksonConstants.ENABLE_CUSTOM_CONFIGURATION, this.isEnableCustomConfiguration()));
        mapper.setDescrializationConfig(mapper.getDeserializationConfig().withAttribute(JacksonConstants.ENABLE_CUSTOM_CONFIGURATION, this.isEnableCustomConfiguration()));
        configNulls(mapper);
        configBoolean(mapper);
        configNumber(mapper);
        configEnum(mapper);
        configDate(mapper);
        if (!serializeNonFieldGetter()) {
            mapper.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
        }
        mapper.configure(SerializationFeature.INDENT_OUTPUT, prettyFormat());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JacksonAdapter jsonHandler = new JacksonAdapter();
        jsonHandler.setObjectMapper(mapper);
        JSON json = new JSON();
        json.setJsonHandler(jsonHandler);
        return json;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        JacksonJSONBuilder result = new JacksonJSONBuilder(this.getExclusionConfiguration());
        this.copyTo(result);
        return result;
    }
}
