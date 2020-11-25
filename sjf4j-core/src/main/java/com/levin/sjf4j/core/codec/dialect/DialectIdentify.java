package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.util.Objects;
import com.jn.langx.util.hash.HashCodeBuilder;

public final class DialectIdentify {
    private String id;
    private String libUrl;

    public DialectIdentify(){}

    public DialectIdentify(String id, String libUrl){
        setId(id);
        setLibUrl(libUrl);
    }

    public boolean isFastjson(){
        return "fastjson".equals(id);
    }

    public boolean isGson(){
        return "gson".equals(id);
    }

    public boolean isJackson(){
        return "jackson".equals(id);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibUrl() {
        return this.libUrl;
    }

    public void setLibUrl(String libUrl) {
        this.libUrl = libUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DialectIdentify)) return false;

        DialectIdentify that = (DialectIdentify) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }

        if (!Objects.equals(libUrl, that.libUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().with(id).with(libUrl).build();
    }
}
