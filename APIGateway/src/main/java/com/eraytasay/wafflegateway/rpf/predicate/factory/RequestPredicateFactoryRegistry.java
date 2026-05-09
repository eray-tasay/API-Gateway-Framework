package com.eraytasay.wafflegateway.rpf.predicate.factory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class RequestPredicateFactoryRegistry {
    private final Map<String, IRequestPredicateFactory> m_factories;

    public RequestPredicateFactoryRegistry()
    {
        m_factories = new HashMap<>();
        registerFactories();
    }

    public void register(IRequestPredicateFactory factory)
    {
        m_factories.put(factory.name(), factory);
    }

    public IRequestPredicateFactory get(String name)
    {
        var factory = m_factories.get(name);

        if (factory == null)
            throw new IllegalArgumentException("Unknown predicate factory: " + name);

        return factory;
    }

    private void registerFactories()
    {
        register(new MethodPredicateFactory());
        register(new PathPredicateFactory());
        register(new HeaderPredicateFactory());
        register(new HostPredicateFactory());
    }
}
