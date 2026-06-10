package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.rpf.request.Request;

public class RouteResolver {
    private final IRouteProvider m_routeProvider;

    public RouteResolver(IRouteProvider routeProvider)
    {
        m_routeProvider = routeProvider;
    }

    public IRoute resolve(Request request)
    {
        for (var route : m_routeProvider.getRoutes())
            if (route.getMatcher().matches(request))
                return route;

        return null;
    }
}