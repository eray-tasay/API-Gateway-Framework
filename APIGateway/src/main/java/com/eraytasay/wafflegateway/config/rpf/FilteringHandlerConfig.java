package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.chain.FilteringHandler;
import com.eraytasay.wafflegateway.rpf.filter.chain.IRequestForwarder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class FilteringHandlerConfig {
    private final IRequestForwarder m_requestForwarder;

    public FilteringHandlerConfig(IRequestForwarder requestForwarder)
    {
        m_requestForwarder = requestForwarder;
    }

    @Bean
    public FilteringHandler filteringHandler()
    {
        return new FilteringHandler(m_requestForwarder);
    }
}
