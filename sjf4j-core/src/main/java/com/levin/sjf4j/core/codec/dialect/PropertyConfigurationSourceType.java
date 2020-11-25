package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.util.enums.base.CommonEnum;
import com.jn.langx.util.enums.base.EnumDelegate;

public enum PropertyConfigurationSourceType implements CommonEnum {
    FIELD(0, "field", "字段"),
    GETTER(1, "getter", "get方法"),
    SETTER(2, "setter", "set方法");
    private EnumDelegate delegate;

    private PropertyConfigurationSourceType(int code, String name, String displayText) {
        delegate = new EnumDelegate(code, name, displayText);
    }

    @Override
    public int getCode() {
        return delegate.getCode();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public String getDisplayText() {
        return delegate.getDisplayText();
    }
}
