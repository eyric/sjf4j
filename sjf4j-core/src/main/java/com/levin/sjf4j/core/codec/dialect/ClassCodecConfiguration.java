package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.reflect.Reflects;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * id 是类全名
 */
public class ClassCodecConfiguration extends CodecConfiguration {
    private WeakReference clazzRef;
    private List<String> includedPropertyNames = Collects.emptyArrayList();
    private List<String> excludedPropertyNames= Collects.emptyArrayList();

    public Class getClazz() {
        return clazzRef.getClass();
    }

    public void setClazz(Class clazz) {
        this.clazzRef = new WeakReference(clazz);
        this.setId(Reflects.getFQNClassName(clazz));
    }
    public void addIncludedPropertyName(String propertyName){
        includedPropertyNames.add(propertyName);
    }
    public void addExcludedPropertyName(String propertyName){
        excludedPropertyNames.add(propertyName);
    }

    public List<String> getIncludedPropertyNames() {
        return includedPropertyNames;
    }

    public void setIncludedPropertyNames(List<String> includedPropertyNames) {
        this.includedPropertyNames = includedPropertyNames;
    }

    public List<String> getExcludedPropertyNames() {
        return excludedPropertyNames;
    }

    public void setExcludedPropertyNames(List<String> excludedPropertyNames) {
        this.excludedPropertyNames = excludedPropertyNames;
    }
}
