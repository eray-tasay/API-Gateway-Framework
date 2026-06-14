package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public class AddPostfixPathFilter implements IRequestFilter {
    private final String m_postfix;

    public AddPostfixPathFilter(String postfix)
    {
        m_postfix = normalize(postfix);
    }

    @Override
    public void filter(RequestContext context, IFilterChain chain)
    {
        var request = context.getExchangeRequest();
        var currentPath = request.getPath();

        if (currentPath.endsWith("/"))
            request.setPath(currentPath + m_postfix.substring(1));
        else
            request.setPath(currentPath + m_postfix);

        chain.next(context);
    }

    private String normalize(String prefix)
    {
        if (prefix == null || prefix.isEmpty())
            return "/";

        return prefix.startsWith("/") ? prefix : "/" + prefix;
    }
}
