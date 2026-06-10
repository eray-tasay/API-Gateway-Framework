package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.core.GatewayHandler;
import com.eraytasay.wafflegateway.rpf.filter.chain.FilteringHandler;
import com.eraytasay.wafflegateway.rpf.route.RouteResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class GatewayHandlerConfig {
    private final RouteResolver m_routeResolver;
    private final FilteringHandler m_filteringHandler;

    public GatewayHandlerConfig(RouteResolver routeResolver, FilteringHandler filteringHandler)
    {
        m_routeResolver = routeResolver;
        m_filteringHandler = filteringHandler;
    }

    @Bean
    public GatewayHandler gatewayHandler()
    {
        return new GatewayHandler(m_routeResolver, m_filteringHandler);
    }
}
