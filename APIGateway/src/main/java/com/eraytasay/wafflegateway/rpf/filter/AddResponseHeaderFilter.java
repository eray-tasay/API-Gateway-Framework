package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public class AddResponseHeaderFilter implements IRequestFilter {
    private final String m_name;
    private final String m_value;

    public AddResponseHeaderFilter(String name, String value)
    {
        m_name = name;
        m_value = value;
    }

    @Override
    public void filter(RequestContext context, IFilterChain chain)
    {
        chain.next(context);

        var response = context.getResponse();

        response.getHeaders().mutate(m_name).addLast(m_value);
    }
}
