package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.predicate.factory.RequestPredicateFactoryRegistry;
import com.eraytasay.wafflegateway.rpf.predicate.parser.RequestPredicateParser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestPredicateParserConfig {
    private final RequestPredicateFactoryRegistry m_registry;

    public RequestPredicateParserConfig(RequestPredicateFactoryRegistry registry)
    {
        m_registry = registry;
    }

    @Bean
    public RequestPredicateParser requestPredicateParser()
    {
        return new RequestPredicateParser(m_registry);
    }
}
