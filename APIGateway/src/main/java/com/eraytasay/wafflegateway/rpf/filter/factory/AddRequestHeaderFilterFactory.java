package com.eraytasay.wafflegateway.rpf.filter.factory;

import com.eraytasay.wafflegateway.rpf.filter.AddRequestHeaderFilter;
import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.util.List;

public final class AddRequestHeaderFilterFactory implements IRequestFilterFactory {
    @Override
    public String name()
    {
        return "AddRequestHeader";
    }

    @Override
    public IRequestFilter create(List<String> args)
    {
        if (args.size() != 2)
            throw new IllegalArgumentException("AddRequestHeader requires exactly two arguments");

        return new AddRequestHeaderFilter(
            args.get(0),
            args.get(1)
        );
    }
}