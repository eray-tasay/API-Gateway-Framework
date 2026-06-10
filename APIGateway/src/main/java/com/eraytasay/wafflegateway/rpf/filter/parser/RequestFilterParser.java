package com.eraytasay.wafflegateway.rpf.filter.parser;

import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;
import com.eraytasay.wafflegateway.rpf.filter.factory.RequestFilterFactoryRegistry;

import java.util.Arrays;

public final class RequestFilterParser {
    private final RequestFilterFactoryRegistry m_registry;

    public RequestFilterParser(RequestFilterFactoryRegistry registry)
    {
        m_registry = registry;
    }

    public IRequestFilter parse(String definition)
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
