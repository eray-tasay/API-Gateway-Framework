package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;
import com.eraytasay.wafflegateway.rpf.request.IRequest;

import java.util.ArrayList;
import java.util.List;

public final class RequestMatcher implements IRequestMatcher {
    private final List<IRequestPredicate> m_predicates;

    public RequestMatcher(List<IRequestPredicate> predicates)
    {
        m_predicates = new ArrayList<>(predicates);
    }

    private RequestMatcher(Builder builder)
    {
        m_predicates = builder.m_predicates;
    }

    public boolean matches(IRequest request)
    {
        for (var predicate : m_predicates)
            if (!predicate.test(request))
                return false;

        return true;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final List<IRequestPredicate> m_predicates = new ArrayList<>();

        public Builder predicate(IRequestPredicate predicate)
        {
            m_predicates.add(predicate);
            return this;
        }

        public Builder and(IRequestPredicate predicate)
        {
            m_predicates.add(predicate);
            return this;
        }

        public RequestMatcher build()
        {
            return new RequestMatcher(this);
        }
    }
}
