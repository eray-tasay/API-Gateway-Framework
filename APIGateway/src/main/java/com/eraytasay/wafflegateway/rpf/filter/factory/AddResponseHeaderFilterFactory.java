package com.eraytasay.wafflegateway.rpf.filter.factory;

import com.eraytasay.wafflegateway.rpf.filter.AddResponseHeaderFilter;
import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.util.List;

public final class AddResponseHeaderFilterFactory implements IRequestFilterFactory {
    @Override
    public String name()
    {
        return "AddResponseHeader";
    }

    @Override
    public IRequestFilter create(List<String> args)
    {
        if (args.size() != 2)
            throw new IllegalArgumentException("AddResponseHeader requires exactly two arguments");

        return new AddResponseHeaderFilter(
            args.get(0),
            args.get(1)
        );
    }
}
