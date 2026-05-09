package com.eraytasay.wafflegateway.rpf.predicate.factory;

import com.eraytasay.wafflegateway.rpf.predicate.HostPredicate;
import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;

import java.util.List;

public final class HostPredicateFactory implements IRequestPredicateFactory
{
    @Override
    public String name()
    {
        return "Host";
    }

    @Override
    public IRequestPredicate create(List<String> args)
    {
        if (args.size() != 1)
            throw new IllegalArgumentException("Host predicate requires exactly one argument");

        return new HostPredicate(args.getFirst());
    }
}