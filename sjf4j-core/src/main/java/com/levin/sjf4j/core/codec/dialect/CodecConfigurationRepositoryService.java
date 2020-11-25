package com.levin.sjf4j.core.codec.dialect;

import com.jn.langx.annotation.Singleton;
import com.jn.langx.lifecycle.Initializable;
import com.jn.langx.lifecycle.InitializationException;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@Singleton
public class CodecConfigurationRepositoryService implements Initializable {
    private static final Map<DialectIdentify, CodecConfigurationRepository> repositoryMap = new HashMap<DialectIdentify, CodecConfigurationRepository>();
    private static final CodecConfigurationRepositoryService INSTANCE = new CodecConfigurationRepositoryService();

    private volatile boolean inited = false;
    private CodecConfigurationRepositoryService(){
        init();
    }

    public static CodecConfigurationRepositoryService getInstance(){
        return INSTANCE;
    }

    @Override
    public void init() throws InitializationException {
        if(!inited){
            inited = true;
            ServiceLoader<CodecConfigurationRepository> serviceLoader = ServiceLoader.load(CodecConfigurationRepository.class);
            Collects.forEach(serviceLoader, new Consumer<CodecConfigurationRepository>() {
                @Override
                public void accept(CodecConfigurationRepository codecConfigurationRepository) {
                    register(codecConfigurationRepository);
                }
            });
        }
    }

    public void register(CodecConfigurationRepository codecConfigurationRepository){
        repositoryMap.put(codecConfigurationRepository.getDialectIdentify(), codecConfigurationRepository);
    }

    public CodecConfigurationRepository getCodecConfigurationRepository(DialectIdentify dialectIdentify){
        return repositoryMap.get(dialectIdentify);
    }
}
