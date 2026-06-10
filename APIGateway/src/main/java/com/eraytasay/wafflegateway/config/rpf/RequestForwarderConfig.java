package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.chain.IRequestProvider;
import com.eraytasay.wafflegateway.rpf.filter.chain.RequestForwarder;
import com.eraytasay.wafflegateway.rpf.filter.chain.RequestSender;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestForwarderConfig {
    private final IRequestProvider m_requestProvider;
    private final RequestSender m_requestSender;

    public RequestForwarderConfig(IRequestProvider requestProvider, RequestSender requestSender)
    {
        m_requestProvider = requestProvider;
        m_requestSender = requestSender;
    }

    @Bean
    public RequestForwarder requestForwarder()
    {
        return new RequestForwarder(m_requestProvider, m_requestSender);
    }
}
