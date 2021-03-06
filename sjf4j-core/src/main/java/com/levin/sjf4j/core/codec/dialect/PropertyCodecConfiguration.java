package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.annotation.NonNull;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.reflect.Reflects;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

/**
 * 序列化，或反序列化字段、方法时的配置
 * <p>
 * gson 是基于字段
 * jackson 是基于方法
 * fastjson 都有
 */
public class PropertyCodecConfiguration extends CodecConfiguration {

    /**
     * 字段名，如果是方法，则为去掉了 get,set,is之后的
     */
    private String name;

    /**
     * 别名
     */
    private String alias;


    private PropertyConfigurationSourceType sourceType;

    /**
     * 当 isClassProperty 时, classRef 才有意义
     */
    private WeakReference<Class> clazzRef;

    public static PropertyCodecConfiguration getPropertyCodecConfiguration(@NonNull DialectIdentify dialectIdentify, @NonNull Object container, @NonNull String propertyName) {
        if (container == null) {
            return null;
        }
        return getPropertyCodecConfiguration(dialectIdentify, container.getClass(), propertyName);
    }

    public static PropertyCodecConfiguration getPropertyCodecConfiguration(@NonNull DialectIdentify dialectIdentify, @NonNull Class containerClass, @NonNull String propertyName) {
        if (dialectIdentify == null) {
            return null;
        }
        if (containerClass == null) {
            return null;
        }
        if (Emptys.isEmpty(propertyName)) {
            return null;
        }

        String packageName = Reflects.getPackageName(containerClass);
        if (packageName.startsWith("java.")) {
            return null;
        }

        if (Reflects.isSubClassOrEquals(Map.class, containerClass) || Reflects.isSubClassOrEquals(Collection.class, containerClass) || containerClass.isArray()) {
            return null;
        }
        CodecConfigurationRepository codecConfigurationRepository = CodecConfigurationRepositoryService.getInstance().getCodecConfigurationRepository(dialectIdentify);
        if (codecConfigurationRepository == null) {
            return null;
        }
        return codecConfigurationRepository.getPropertyCodeConfiguration(containerClass, propertyName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Class getClazz() {
        return clazzRef == null ? null : clazzRef.get();
    }

    public void setClazz(Class clazz) {
        this.clazzRef = new WeakReference<Class>(clazz);
    }

    public PropertyConfigurationSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(PropertyConfigurationSourceType sourceType) {
        this.sourceType = sourceType;
    }
}
