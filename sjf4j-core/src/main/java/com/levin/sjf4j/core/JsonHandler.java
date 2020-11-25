package com.levin.sjf4j.core;

import java.io.Reader;
import java.lang.reflect.Type;

public interface JsonHandler {

    JsonTreeNode deserialize(String json) throws JsonException;

    <T> T deserialize(String json, Type typeOfT) throws JsonException;

    <T> T deserialize(Reader reader, Type typeOfT) throws JsonException;

    String serialize(Object src, Type typeOfT) throws JsonException;

}
