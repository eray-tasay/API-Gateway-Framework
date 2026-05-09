package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.net.URI;
import java.util.List;

public final class Route implements IRoute {
    private final String m_id;
    private final URI m_uri;
    private final RequestMatcher m_matcher;
    private final List<IRequestFilter> m_filters;

    public Route(String id, URI uri, RequestMatcher matcher, List<IRequestFilter> filters)
    {
        m_id = id;
        m_uri = uri;
        m_matcher = matcher;
        m_filters = List.copyOf(filters);
    }

    @Override
    public String getId()
    {
        return m_id;
    }

    @Override
    public RequestMatcher getMatcher()
    {
        return m_matcher;
    }

    @Override
    public URI getTargetUri()
    {
        return m_uri;
    }

    /*
     * Instead of associating a route with RequestFilterChain, use a list of filters. This allows creating a filter
     * chain for each incoming request. It enables us to combine global filters with filters of a route.
     * */
    @Override
    public List<IRequestFilter> getFilters()
    {
        return m_filters;
    }
}
