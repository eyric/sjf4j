package com.levin.sjf4j.jackson.ext;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.levin.sjf4j.jackson.JacksonJSONBuilder;

public class EasyJsonObjectMapper extends ObjectMapper {

    private JacksonJSONBuilder jsonBuilder;

    public EasyJsonObjectMapper() {
        super();
        setDefaultDeserializationContext(new DefaultDeserializationContext.Impl(new EasyJsonBeanDeserializerFactory(new DeserializerFactoryConfig(), this)));
        setSerializerFactory(new EasyJsonBeanSerializerFactory(null, this));
    }

    public EasyJsonObjectMapper(ObjectMapper src) {
        super(src);
        setDefaultDeserializationContext(new DefaultDeserializationContext.Impl(new EasyJsonBeanDeserializerFactory(new DeserializerFactoryConfig(), this)));
        setSerializerFactory(new EasyJsonBeanSerializerFactory(null, this));
    }

    @Override
    public DeserializationConfig getDeserializationConfig() {
        return super.getDeserializationConfig();
    }

    public void setDescrializationConfig(DeserializationConfig config) {
        this._deserializationConfig = config;
    }

    public void setSerializationConfig(SerializationConfig config) {
        this._serializationConfig = config;
    }

    public void setDefaultDeserializationContext(DefaultDeserializationContext context) {
        this._deserializationContext = context;
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
        DefaultSerializerProvider provider = _serializerProvider.createInstance(config, _serializerFactory);
        if(!jsonBuilder.serializeNulls()){

        }
        return provider;
    }

    public JacksonJSONBuilder getJsonBuilder() {
        return jsonBuilder;
    }

    public void setJsonBuilder(JacksonJSONBuilder jsonBuilder) {
        this.jsonBuilder = jsonBuilder;
    }
}
