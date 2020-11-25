package com.levin.sjf4j.core.codec.dialect;

public interface PropertyCodecConfigurationMerger {
    public void merge(PropertyCodecConfiguration baseConfiguration, PropertyCodecConfiguration newConfiguration);
}
