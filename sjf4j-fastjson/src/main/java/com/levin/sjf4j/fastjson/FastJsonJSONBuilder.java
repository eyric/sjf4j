package com.levin.sjf4j.fastjson;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.levin.sjf4j.core.JSON;
import com.levin.sjf4j.core.JSONBuilder;
import com.levin.sjf4j.core.annotation.DependOn;
import com.levin.sjf4j.core.codec.dialect.DialectIdentify;
import com.levin.sjf4j.core.exclusion.ExclusionConfiguration;
import com.levin.sjf4j.core.tree.JsonTreeSerializerBuilder;
import com.levin.sjf4j.fastjson.codec.BooleanCodec;
import com.levin.sjf4j.fastjson.codec.DateCodec;
import com.levin.sjf4j.fastjson.codec.NumberCodec;
import com.levin.sjf4j.fastjson.ext.EasyJsonParserConfig;
import com.levin.sjf4j.fastjson.ext.EasyJsonSerializeConfig;
import com.jn.langx.annotation.Name;
import com.jn.langx.util.reflect.Reflects;

@Name("fastjson")
@DependOn("com.alibaba.fastjson.JSON")
public class FastJsonJSONBuilder extends JSONBuilder {

    public static final DialectIdentify FASTJSON = new DialectIdentify();
    static {
        FASTJSON.setId("fastjson");
        try {
            Class clazz = Class.forName("com.alibaba.fastjson.JSON");
            FASTJSON.setLibUrl(Reflects.getCodeLocation(clazz).toString());
        }catch (Throwable ex){
            // ignore it
        }
    }
    public FastJsonJSONBuilder() {
        super();
        dialectIdentify(FASTJSON);
    }

    public FastJsonJSONBuilder(ExclusionConfiguration exclusionConfiguration) {
        super(exclusionConfiguration);
        dialectIdentify(FASTJSON);
    }

    @Override
    public JSON build() {
        FastJsonSerializerBuilder serializerBuilder = buildSerializer();
        FastJsonParserBuilder deserializerBuilder = buildDeserializer();
        JsonTreeSerializerBuilder jsonTreeSerializerBuilder = buildJsonTreeWriter();

        // boolean
        BooleanCodec booleanCodec = new BooleanCodec();
        booleanCodec.setUsing1_0(serializeBooleanUsing1_0());
        booleanCodec.setUsingOnOff(serializeBooleanUsingOnOff());
        serializerBuilder.apply(booleanCodec);
        deserializerBuilder.apply(booleanCodec);

        // number
        NumberCodec numberCodec = new NumberCodec();
        numberCodec.setLongUsingString(serializeLongAsString());
        numberCodec.setUsingString(serializeNumberAsString());
        serializerBuilder.apply(numberCodec);
        deserializerBuilder.apply(numberCodec);

        // date
        DateCodec dateCodec = new DateCodec();
        dateCodec.setDatePattern(serializeDateUsingPattern());
        dateCodec.setDateFormat(serializeUseDateFormat());
        dateCodec.setUsingToString(serializeDateUsingToString());
        serializerBuilder.apply(dateCodec);
        deserializerBuilder.apply(dateCodec);

        FastJson fastJson = new FastJson(serializerBuilder, deserializerBuilder, jsonTreeSerializerBuilder);
        FastJsonAdapter jsonHandler = new FastJsonAdapter();
        jsonHandler.setFastJson(fastJson);
        return new JSON().setJsonHandler(jsonHandler);
    }

    private JsonTreeSerializerBuilder buildJsonTreeWriter() {
        return new JsonTreeSerializerBuilder().setPrettyFormat(prettyFormat()).setSerializeNulls(serializeNulls());
    }

    private FastJsonSerializerBuilder buildSerializer() {
        SerializeConfig config = new EasyJsonSerializeConfig(this);
        FastJsonSerializerBuilder builder = new FastJsonSerializerBuilder();
        builder.config(config);
        builder.addFeature(SerializerFeature.DisableCircularReferenceDetect);
        builder.addFeature(SerializerFeature.SkipTransientField);
        builder.addFeature(SerializerFeature.IgnoreErrorGetter);
        if(!serializeNonFieldGetter()) {
            builder.addFeature(SerializerFeature.IgnoreNonFieldGetter);
        }

        // SerializerFeature.WriteNullStringAsEmpty ==> ""
        // SerializerFeature.WriteNullListAsEmpty ==>[]

        if (serializeNulls()) {
            builder.addFeature(SerializerFeature.WriteMapNullValue);
        }

        if (serializeEnumUsingToString()) {
            builder.addFeature(SerializerFeature.WriteEnumUsingToString);
        }
        if (prettyFormat()) {
            builder.addFeature(SerializerFeature.PrettyFormat);
        }
        return builder;
    }

    private FastJsonParserBuilder buildDeserializer() {
        ParserConfig config = new EasyJsonParserConfig(this);
        int featureValues = com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;
        FastJsonParserBuilder builder = new FastJsonParserBuilder().config(config).defaultFeatureValues(featureValues);
        builder.addFeature(Feature.DisableCircularReferenceDetect);
        return builder;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FastJsonJSONBuilder result = new FastJsonJSONBuilder(this.getExclusionConfiguration());
        this.copyTo(result);
        return result;
    }
}
