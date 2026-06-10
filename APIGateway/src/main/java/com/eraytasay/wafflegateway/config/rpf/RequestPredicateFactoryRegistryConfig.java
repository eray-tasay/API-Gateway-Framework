package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.predicate.factory.RequestPredicateFactoryRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestPredicateFactoryRegistryConfig {
    @Bean
    public RequestPredicateFactoryRegistry requestPredicateFactoryRegistry()
    {
        return new RequestPredicateFactoryRegistry();
    }
}
