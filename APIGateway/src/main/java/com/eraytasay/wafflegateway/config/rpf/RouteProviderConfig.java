package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.parser.RequestFilterParser;
import com.eraytasay.wafflegateway.rpf.predicate.parser.RequestPredicateParser;
import com.eraytasay.wafflegateway.rpf.route.IRouteProvider;
import com.eraytasay.wafflegateway.rpf.route.RouteDefinitionRouteProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RouteProviderConfig {
    private final GatewayProperties m_gatewayProperties;
    private final RequestPredicateParser m_predicateParser;
    private final RequestFilterParser m_filterParser;

    public RouteProviderConfig(GatewayProperties properties,
                                        RequestPredicateParser predicateParser,
                                        RequestFilterParser filterParser)
    {
        m_gatewayProperties = properties;
        m_predicateParser = predicateParser;
        m_filterParser = filterParser;
    }

    @Bean
    public IRouteProvider routeProvider()
    {
        return new RouteDefinitionRouteProvider(m_gatewayProperties, m_predicateParser, m_filterParser);
    }
}
