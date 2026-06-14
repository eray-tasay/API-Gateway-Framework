package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public final class PathPredicate implements IRequestPredicate {
    private final String m_path;

    public PathPredicate(String path)
    {
        m_path = normalize(path);
    }

    @Override
    public boolean test(IRequest request)
    {
        return request.getPath().equals(m_path);
    }

    private String normalize(String prefix)
    {
        if (prefix == null || prefix.isEmpty())
            return "/";

        return prefix.startsWith("/") ? prefix : "/" + prefix;
    }
}
