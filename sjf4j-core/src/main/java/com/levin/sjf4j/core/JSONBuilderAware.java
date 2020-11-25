package com.levin.sjf4j.core;

public interface JSONBuilderAware<T extends JSONBuilder> {
    void setJSONBuilder(T jsonBuilder);
    T getJSONBuilder();
}
