package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public final class HostPredicate implements IRequestPredicate {
    private final String m_host;

    public HostPredicate(String host)
    {
        m_host = host;
    }

    @Override
    public boolean test(IRequest request)
    {
        var header = request.getHeaders().get("Host");

        if (header == null || header.count() != 1)
            return false;

        return header.getFirst().equalsIgnoreCase(m_host);
    }
}
