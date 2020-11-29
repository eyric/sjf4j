package com.levin.sjf4j.fastjson.ext;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.jn.langx.util.reflect.Reflects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SerializeBeanInfoGetter {
    private static final Logger logger = LoggerFactory.getLogger(SerializeBeanInfoGetter.class);
    private SerializeBeanInfo beanInfo;

    public SerializeBeanInfoGetter(SerializeBeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    public Class getBeanType() {
        Field field = Reflects.getDeclaredField(beanInfo.getClass(), "beanType");
        if (field != null) {
            field.setAccessible(true);
            try {
                return (Class) field.get(beanInfo);
            } catch (IllegalAccessException e) {
                // ignore it
            }
        }
        return null;
    }

    public String getTypeName() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "typeName", true, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTypeKey() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "typeKey", true, true);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public JSONType getJsonType() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "jsonType", true, true);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public FieldInfo[] getFields() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "fields", true, true);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public FieldInfo[] getSortedFields() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "sortedFields", true, true);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Integer getFeatures() {
        try {
            return Reflects.getDeclaredFieldValue(beanInfo, "features", true, true);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
