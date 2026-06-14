package com.eraytasay.wafflegateway.rpf.filter.factory;

import java.util.HashMap;
import java.util.Map;

public final class RequestFilterFactoryRegistry {
    private final Map<String, IRequestFilterFactory> m_factories;

    public RequestFilterFactoryRegistry()
    {
        m_factories = new HashMap<>();
        registerFactories();
    }

    public void register(IRequestFilterFactory factory)
    {
        m_factories.put(factory.name(), factory);
    }

    public IRequestFilterFactory get(String name)
    {
        IRequestFilterFactory factory = m_factories.get(name);

        if (factory == null)
            throw new IllegalArgumentException("Unknown filter factory: " + name);

        return factory;
    }

    private void registerFactories()
    {
        register(new AddRequestHeaderFilterFactory());
        register(new AddResponseHeaderFilterFactory());
        register(new StripPathPrefixFilterFactory());
        register(new AddPostfixPathFilterFactory());
        register(new RewritePathFilterFactory());
    }
}
