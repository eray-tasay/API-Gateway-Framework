package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public final class PathPrefixPredicate implements IRequestPredicate {
    private final String m_prefix;

    public PathPrefixPredicate(String prefix)
    {
        m_prefix = normalize(prefix);
    }

    @Override
    public boolean test(IRequest request)
    {
        return matchesPrefix(request.getPath());
    }

    private String normalize(String prefix)
    {
        if (prefix == null || prefix.isEmpty())
            return "/";

        return prefix.startsWith("/") ? prefix : "/" + prefix;
    }

    private boolean matchesPrefix(String path)
    {
        if (!path.startsWith(m_prefix))
            return false;

        return path.length() == m_prefix.length()
                || path.charAt(m_prefix.length()) == '/';
    }
}
