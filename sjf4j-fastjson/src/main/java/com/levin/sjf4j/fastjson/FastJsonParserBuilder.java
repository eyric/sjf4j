package com.levin.sjf4j.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.levin.sjf4j.fastjson.codec.Typed;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FastJsonParserBuilder {
    private ParserConfig config;
    private List<Feature> features = new ArrayList<Feature>();
    private int defaultFeatureValues = JSON.DEFAULT_PARSER_FEATURE;

    public FastJsonParserBuilder config(ParserConfig config) {
        this.config = config;
        return this;
    }

    public FastJsonParserBuilder defaultFeatureValues(int value) {
        defaultFeatureValues = value;
        return this;
    }

    public FastJsonParserBuilder addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public FastJsonParserBuilder apply(ObjectDeserializer deserializer) {
        if (deserializer instanceof Typed) {
            List<Type> applyTo = ((Typed) deserializer).applyTo();
            if (applyTo != null && !applyTo.isEmpty()) {
                for (Type type : applyTo) {
                    apply(type, deserializer);
                }
            }
        }
        return this;
    }

    public FastJsonParserBuilder apply(Type type, ObjectDeserializer deserializer) {
        config.putDeserializer(type, deserializer);
        return this;
    }

    public DefaultJSONParser build(String jsonString) {
        return new DefaultJSONParser(jsonString, config, getFeatures());
    }

    public int getFeatures(){
        int featureValues = defaultFeatureValues;
        for (Feature feature : features) {
            featureValues |= feature.getMask();
        }
        return featureValues;
    }
}
