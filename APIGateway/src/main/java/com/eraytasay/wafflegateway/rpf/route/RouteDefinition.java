package com.eraytasay.wafflegateway.rpf.route;

import java.net.URI;
import java.util.List;

/*
* This is a DTO class, so did not care about encapsulation.
* */
public final class RouteDefinition {
    private String m_id;
    private URI m_uri;
    private List<String> m_predicates;
    private List<String> m_filters;

    public String getId()
    {
        return m_id;
    }

    public void setId(String id)
    {
        m_id = id;
    }

    public URI getUri()
    {
        return m_uri;
    }

    public void setUri(URI uri)
    {
        m_uri = uri;
    }

    public List<String> getPredicates()
    {
        return m_predicates;
    }

    public void setPredicates(List<String> predicates)
    {
        m_predicates = predicates;
    }

    public List<String> getFilters()
    {
        return m_filters;
    }

    public void setFilters(List<String> filters)
    {
        m_filters = filters;
    }
}
