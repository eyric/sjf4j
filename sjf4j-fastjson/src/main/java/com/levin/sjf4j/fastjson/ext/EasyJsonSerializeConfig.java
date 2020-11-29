package com.levin.sjf4j.fastjson.ext;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.levin.sjf4j.core.exclusion.ExclusionConfiguration;
import com.levin.sjf4j.fastjson.FastJsonJSONBuilder;

import java.util.ArrayList;
import java.util.List;

public class EasyJsonSerializeConfig extends SerializeConfig {
    private FastJsonJSONBuilder jsonJSONBuilder;

    public EasyJsonSerializeConfig(FastJsonJSONBuilder jsonJSONBuilder) {
        super();
        this.jsonJSONBuilder = jsonJSONBuilder;
    }

    @Override
    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        //=====================EasyJson exclusion start==========================
        SerializeBeanInfoGetter getter = new SerializeBeanInfoGetter(beanInfo);
        Class<?> beanType = getter.getBeanType();
        ExclusionConfiguration exclusionConfiguration = jsonJSONBuilder.getExclusionConfiguration();
        if (!exclusionConfiguration.isExcludedClass(beanType, true)) {
            FieldInfo[] fields = getter.getFields();
            FieldInfo[] sortedFields = getter.getSortedFields();

            List<FieldInfo> fieldInfoes = new ArrayList<FieldInfo>();
            List<FieldInfo> sortedFieldInfoes = new ArrayList<FieldInfo>();

            for (FieldInfo fieldInfo : fields) {
                if (FieldInfos.isField(fieldInfo)) {
                    if (exclusionConfiguration.isExcludedField(fieldInfo.field, true)) {
                        continue;
                    }
                }
                fieldInfoes.add(fieldInfo);
            }

            for (FieldInfo fieldInfo : sortedFields) {
                if (FieldInfos.isField(fieldInfo)) {
                    if (exclusionConfiguration.isExcludedField(fieldInfo.field, true)) {
                        continue;
                    }
                }
                sortedFieldInfoes.add(fieldInfo);
            }

            if (fieldInfoes.size() != fields.length) {
                // has ignored field
                String typeName = getter.getTypeName();
                String typeKey = getter.getTypeKey();
                JSONType jsonType = getter.getJsonType();
                int features = getter.getFeatures();
                SerializeBeanInfo newBeanInfo = new SerializeBeanInfo(beanType,
                        jsonType,
                        typeName,
                        typeKey,
                        features,
                        fieldInfoes.toArray(new FieldInfo[fieldInfoes.size()]),
                        sortedFieldInfoes.toArray(new FieldInfo[sortedFieldInfoes.size()]));
                return super.createJavaBeanSerializer(newBeanInfo);
            }
        } else {
            String typeName = getter.getTypeName();
            String typeKey = getter.getTypeKey();
            JSONType jsonType = getter.getJsonType();
            int features = getter.getFeatures();
            SerializeBeanInfo newBeanInfo = new SerializeBeanInfo(beanType,
                    jsonType,
                    typeName,
                    typeKey,
                    features,
                    new FieldInfo[0],
                    new FieldInfo[0]);
            return super.createJavaBeanSerializer(newBeanInfo);
        }

        //=====================EasyJson exclusion end==========================

        return super.createJavaBeanSerializer(beanInfo);
    }


}
