package com.eraytasay.wafflegateway.rpf.predicate.factory;

import com.eraytasay.wafflegateway.rpf.predicate.PathPrefixPredicate;
import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;

import java.util.List;

public final class PathPredicateFactory implements IRequestPredicateFactory
{
    @Override
    public String name()
    {
        return "Path";
    }

    @Override
    public IRequestPredicate create(List<String> args)
    {
        if (args.size() != 1)
            throw new IllegalArgumentException("Path predicate requires exactly one argument");

        return new PathPrefixPredicate(args.getFirst());
    }
}