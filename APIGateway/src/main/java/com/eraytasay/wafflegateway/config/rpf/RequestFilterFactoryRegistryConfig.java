package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.factory.RequestFilterFactoryRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestFilterFactoryRegistryConfig {
    @Bean
    public RequestFilterFactoryRegistry requestFilterFactoryRegistry()
    {
        return new RequestFilterFactoryRegistry();
    }
}
