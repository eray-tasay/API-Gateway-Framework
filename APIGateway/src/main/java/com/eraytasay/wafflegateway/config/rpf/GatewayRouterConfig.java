package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.core.GatewayHandler;
import com.eraytasay.wafflegateway.rpf.core.GatewayRouter;
import com.eraytasay.wafflegateway.rpf.request.RequestMapper;
import com.eraytasay.wafflegateway.rpf.response.ResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class GatewayRouterConfig {
    private final ResponseWriter m_responseWriter;
    private final RequestMapper m_requestMapper;
    private final GatewayHandler m_gatewayHandler;

    public GatewayRouterConfig(ResponseWriter responseWriter, RequestMapper requestMapper, GatewayHandler gatewayHandler)
    {
        m_responseWriter = responseWriter;
        m_requestMapper = requestMapper;
        m_gatewayHandler = gatewayHandler;
    }

    @Bean
    public GatewayRouter gatewayRouter()
    {
        return new GatewayRouter(m_responseWriter, m_requestMapper, m_gatewayHandler);
    }
}
