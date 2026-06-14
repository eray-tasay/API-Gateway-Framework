package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public class StripPathPrefixFilter implements IRequestFilter {
    private final String m_prefix;

    public StripPathPrefixFilter(String prefix)
    {
        m_prefix = normalize(prefix);
    }

    @Override
    public void filter(RequestContext context, IFilterChain chain)
    {
        var request = context.getExchangeRequest();

        var originalPath = request.getPath();
        var newPath = stripPrefix(originalPath);

        request.setPath(newPath);

        chain.next(context);
    }

    private String stripPrefix(String path)
    {
        if (path == null)
            return null;

        if (!matchesPrefix(path))
            return path;

        var stripped = path.substring(m_prefix.length());

        if (stripped.isEmpty())
            return "/";

        if (!stripped.startsWith("/"))
            return "/" + stripped;

        return stripped;
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
