package com.levin.sjf4j.gson.exclusion;

import com.jn.langx.util.reflect.FieldAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;

public class GsonFieldAttributesAdapter extends FieldAttributes {
    private com.google.gson.FieldAttributes delegate;

    public GsonFieldAttributesAdapter(Field f) {
        this(new com.google.gson.FieldAttributes(f), f);
    }

    public GsonFieldAttributesAdapter(com.google.gson.FieldAttributes f, Field field) {
        super(field);
        this.delegate = f;
    }

    @Override
    public Class<?> getDeclaringClass() {
        return delegate.getDeclaringClass();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Type getDeclaredType() {
        return delegate.getDeclaredType();
    }

    @Override
    public Class<?> getDeclaredClass() {
        return delegate.getDeclaredClass();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return delegate.getAnnotation(annotation);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return delegate.getAnnotations();
    }

    @Override
    public boolean hasModifier(int modifier) {
        return delegate.hasModifier(modifier);
    }
}
