package com.eraytasay.wafflegateway.rpf.core;

import com.eraytasay.wafflegateway.exception.NoMatchingRouteException;
import com.eraytasay.wafflegateway.rpf.filter.chain.FilteringHandler;
import com.eraytasay.wafflegateway.rpf.request.Request;
import com.eraytasay.wafflegateway.rpf.response.Response;
import com.eraytasay.wafflegateway.rpf.route.IRoute;
import com.eraytasay.wafflegateway.rpf.route.RouteResolver;
import org.springframework.stereotype.Component;

@Component
public class GatewayHandler {
    private final RouteResolver m_routeResolver;
    private final FilteringHandler m_filteringHandler;

    public GatewayHandler(RouteResolver routeResolver, FilteringHandler filteringHandler)
    {
        m_routeResolver = routeResolver;
        m_filteringHandler = filteringHandler;
    }

    public Response handle(Request request)
    {
        IRoute route = m_routeResolver.resolve(request);

        if (route == null)
            throw new NoMatchingRouteException("No matching route");

        return m_filteringHandler.handle(route, request);
    }
}
