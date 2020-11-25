package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.util.Objects;
import com.jn.langx.util.hash.HashCodeBuilder;

public class BeanPropertyId {
    private String beanClass;
    private String propertyName;

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if(o==this){
            return true;
        }

        if(o==null || o.getClass()!=BeanPropertyId.class){
            return false;
        }
        BeanPropertyId ID = (BeanPropertyId)o;

        if(!Objects.equals(ID.beanClass, beanClass)){
            return false;
        }

        if(!Objects.equals(ID.propertyName, propertyName)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().with(beanClass).with(propertyName).build();
    }
}
