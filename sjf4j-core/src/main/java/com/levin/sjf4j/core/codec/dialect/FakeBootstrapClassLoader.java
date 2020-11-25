package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.annotation.Singleton;

@Singleton
public class FakeBootstrapClassLoader extends ClassLoader {
    private static final FakeBootstrapClassLoader INSTANCE = new FakeBootstrapClassLoader();

    private FakeBootstrapClassLoader() {
    }

    public static FakeBootstrapClassLoader getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "FakeBootStrapClassLoader";
    }
}
