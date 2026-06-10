package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.factory.RequestFilterFactoryRegistry;
import com.eraytasay.wafflegateway.rpf.filter.parser.RequestFilterParser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestFilterParserConfig {
    private final RequestFilterFactoryRegistry m_requestFilterFactoryRegistry;

    public RequestFilterParserConfig(RequestFilterFactoryRegistry requestFilterFactoryRegistry)
    {
        m_requestFilterFactoryRegistry = requestFilterFactoryRegistry;
    }

    @Bean
    public RequestFilterParser requestFilterParser()
    {
        return new RequestFilterParser(m_requestFilterFactoryRegistry);
    }
}
