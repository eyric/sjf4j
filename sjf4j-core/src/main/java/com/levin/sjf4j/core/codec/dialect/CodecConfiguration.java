package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.configuration.Configuration;
import com.jn.langx.util.collection.MapAccessor;

import java.text.DateFormat;
import java.util.HashMap;

public class CodecConfiguration extends MapAccessor implements Configuration {

    private String id;

    /**
     * 当值为null时，是否序列化
     */
    private Boolean serialNull;

    /**
     * 是否要序列化
     */
    private Boolean serialize;

    /**
     * 是否要反序列化
     */
    private Boolean deserialize;

    /**
     * 日期类型的字段，格式
     */
    private DateFormat dateFormat;
    private String datePattern;

    /**
     * 枚举序列化的方式
     */
    private Boolean enumUsingName;
    // 编号
    private Boolean enumUsingIndex;
    private Boolean enumUsingToString;


    private Boolean booleanUsing01;
    private Boolean booleanUsingONOFF;

    public CodecConfiguration(){
        setTarget(new HashMap<String, Object>());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSerialNull() {
        return serialNull;
    }

    public void setSerialNull(Boolean serialNull) {
        this.serialNull = serialNull;
    }

    public Boolean getSerialize() {
        return serialize;
    }

    public void setSerialize(Boolean serialize) {
        this.serialize = serialize;
    }

    public Boolean getDeserialize() {
        return deserialize;
    }

    public void setDeserialize(Boolean deserialize) {
        this.deserialize = deserialize;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public Boolean getEnumUsingName() {
        return enumUsingName;
    }

    public void setEnumUsingName(Boolean enumUsingName) {
        this.enumUsingName = enumUsingName;
    }

    public Boolean getEnumUsingIndex() {
        return enumUsingIndex;
    }

    public void setEnumUsingIndex(Boolean enumUsingIndex) {
        this.enumUsingIndex = enumUsingIndex;
    }

    public Boolean getEnumUsingToString() {
        return enumUsingToString;
    }

    public void setEnumUsingToString(Boolean enumUsingToString) {
        this.enumUsingToString = enumUsingToString;
    }

    public Boolean getBooleanUsing01() {
        return booleanUsing01;
    }

    public void setBooleanUsing01(Boolean booleanUsing01) {
        this.booleanUsing01 = booleanUsing01;
    }

    public Boolean getBooleanUsingONOFF() {
        return booleanUsingONOFF;
    }

    public void setBooleanUsingONOFF(Boolean booleanUsingONOFF) {
        this.booleanUsingONOFF = booleanUsingONOFF;
    }
}
