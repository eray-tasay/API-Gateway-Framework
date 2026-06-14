package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public class RewritePathFilter implements IRequestFilter {
    private final String m_newPath;

    public RewritePathFilter(String newPath)
    {
        m_newPath = normalize(newPath);
    }

    @Override
    public void filter(RequestContext context, IFilterChain chain)
    {
        var request = context.getExchangeRequest();

        request.setPath(m_newPath);

        chain.next(context);
    }

    private String normalize(String prefix)
    {
        if (prefix == null || prefix.isEmpty())
            return "/";

        return prefix.startsWith("/") ? prefix : "/" + prefix;
    }
}
