package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public final class PathPrefixPredicate implements IRequestPredicate {
    private final String m_prefix;

    public PathPrefixPredicate(String prefix)
    {
        m_prefix = prefix;
    }

    @Override
    public boolean test(IRequest request)
    {
        return request.getPath().startsWith(m_prefix);
    }
}