package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public final class AddRequestHeaderFilter implements IRequestFilter {
    private final String m_name;
    private final String m_value;

    public AddRequestHeaderFilter(String name, String value)
    {
        m_name = name;
        m_value = value;
    }

    @Override
    public void filter(RequestContext context, IFilterChain chain)
    {
        var request = context.getExchangeRequest();
        var headers = request.getHeaders();

        headers.mutate(m_name).addLast(m_value);
        chain.next(context);
    }
}
