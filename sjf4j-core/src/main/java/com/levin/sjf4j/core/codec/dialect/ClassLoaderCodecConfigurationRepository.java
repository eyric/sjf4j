package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.configuration.AbstractConfigurationRepository;
import com.jn.langx.configuration.ConfigurationWriter;

public class ClassLoaderCodecConfigurationRepository <T extends CodecConfiguration> extends AbstractConfigurationRepository<T, ClassLoaderCodecConfigurationLoader<T>, ConfigurationWriter<T>> {
}
