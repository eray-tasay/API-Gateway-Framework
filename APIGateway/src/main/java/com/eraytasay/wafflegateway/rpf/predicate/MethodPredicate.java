package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;
import org.springframework.http.HttpMethod;

public final class MethodPredicate implements IRequestPredicate {
    private final HttpMethod m_method;

    public MethodPredicate(HttpMethod method)
    {
        m_method = method;
    }

    @Override
    public boolean test(IRequest request)
    {
        return request.getMethod() == m_method;
    }
}