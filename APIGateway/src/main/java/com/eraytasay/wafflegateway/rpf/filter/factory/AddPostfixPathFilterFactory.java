package com.eraytasay.wafflegateway.rpf.filter.factory;

import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;
import com.eraytasay.wafflegateway.rpf.filter.StripPathPrefixFilter;

import java.util.List;

public final class AddPostfixPathFilterFactory implements IRequestFilterFactory {
    @Override
    public String name()
    {
        return "AddPostfixPath";
    }

    @Override
    public IRequestFilter create(List<String> args)
    {
        if (args.size() != 1)
            throw new IllegalArgumentException("AddPostfixPath requires exactly one arguments");

        return new StripPathPrefixFilter(args.get(0));
    }
}