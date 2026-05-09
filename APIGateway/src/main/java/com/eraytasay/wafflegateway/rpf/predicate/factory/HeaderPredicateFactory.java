package com.eraytasay.wafflegateway.rpf.predicate.factory;

import com.eraytasay.wafflegateway.rpf.predicate.HeaderPredicate;
import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;

import java.util.List;

public final class HeaderPredicateFactory implements IRequestPredicateFactory
{
    @Override
    public String name()
    {
        return "Header";
    }

    @Override
    public IRequestPredicate create(List<String> args)
    {
        if (args.size() != 2)
            throw new IllegalArgumentException("Header predicate requires exactly two arguments");

        return new HeaderPredicate(args.get(0), args.get(1));
    }
}