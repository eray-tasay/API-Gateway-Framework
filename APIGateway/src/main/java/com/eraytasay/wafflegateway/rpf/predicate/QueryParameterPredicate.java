package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public final class QueryParameterPredicate implements IRequestPredicate {
    private final String m_name;
    private final String m_value;

    public QueryParameterPredicate(String name, String value)
    {
        m_name = name;
        m_value = value;
    }

    @Override
    public boolean test(IRequest request) {
        var param = request.getQueryParameters().get(m_name);

        if (param == null || param.count() != 1)
            return false;

        return param.getFirst().equals(m_value);
    }
}
