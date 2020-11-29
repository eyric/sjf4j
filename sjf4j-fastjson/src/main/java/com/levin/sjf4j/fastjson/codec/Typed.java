package com.levin.sjf4j.fastjson.codec;

import java.lang.reflect.Type;
import java.util.List;

public interface Typed {
    List<Type> applyTo();
}
