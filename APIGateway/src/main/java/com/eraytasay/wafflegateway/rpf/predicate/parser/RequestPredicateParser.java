package com.eraytasay.wafflegateway.rpf.predicate.parser;

import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;
import com.eraytasay.wafflegateway.rpf.predicate.factory.RequestPredicateFactoryRegistry;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public final class RequestPredicateParser {
    private final RequestPredicateFactoryRegistry m_registry;

    public RequestPredicateParser(RequestPredicateFactoryRegistry registry)
    {
        m_registry = registry;
    }

    public IRequestPredicate parse(String definition)
    {
        var separatorIndex = definition.indexOf('=');

        if (separatorIndex < 0)
            throw new IllegalArgumentException("Invalid predicate definition: " + definition);

        var name = definition.substring(0, separatorIndex);
        var argsPart = definition.substring(separatorIndex + 1);

        var args = Arrays.stream(argsPart.split(","))
            .map(String::trim)
            .toList();

        return m_registry.get(name).create(args);
    }
}
