package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.route.IRouteProvider;
import com.eraytasay.wafflegateway.rpf.route.RouteResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RouteResolverConfig {
    private final IRouteProvider m_routeProvider;

    public RouteResolverConfig(IRouteProvider routeProvider)
    {
        m_routeProvider = routeProvider;
    }

    @Bean
    public RouteResolver routeResolver()
    {
        return new RouteResolver(m_routeProvider);
    }
}
