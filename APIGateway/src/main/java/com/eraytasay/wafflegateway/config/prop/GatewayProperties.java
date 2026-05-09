package com.eraytasay.wafflegateway.config.prop;

import com.eraytasay.wafflegateway.rpf.route.RouteDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "api-gateway")
public class GatewayProperties {
    private List<RouteDefinition> m_routes = new ArrayList<>();

    public List<RouteDefinition> getRoutes()
    {
        return m_routes;
    }

    public void setRoutes(List<RouteDefinition> routes)
    {
        m_routes = routes;
    }
}
