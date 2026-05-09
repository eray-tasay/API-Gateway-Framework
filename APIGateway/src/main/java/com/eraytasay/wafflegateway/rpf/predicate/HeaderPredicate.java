package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;
import com.eraytasay.wafflegateway.rpf.request.header.IRequestHeader;

public final class HeaderPredicate implements IRequestPredicate {
    private final String m_name;
    private final String m_value;

    public HeaderPredicate(String name, String value)
    {
        m_name = name;
        m_value = value;
    }

    @Override
    public boolean test(IRequest request)
    {
        IRequestHeader header = request.getHeaders().get(m_name);

        if (header == null || header.count() != 1)
            return false;

        return header.getFirst().equals(m_value);
    }
}