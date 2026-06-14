package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.util.ArrayList;
import java.util.List;

public class DefaultFilterChain implements IFilterChain {
    private final List<IRequestFilter> m_filters;
    private final IRequestForwarder m_forwarder;
    private int m_index = 0;

    public DefaultFilterChain(List<IRequestFilter> filters, IRequestForwarder forwarder)
    {
        m_filters = new ArrayList<>(filters);
        m_forwarder = forwarder;
    }

    @Override
    public void next(RequestContext context)
    {
        if (m_index < m_filters.size()) {
            var filter = m_filters.get(m_index++);

            filter.filter(context, this);
            return;
        }

        try {
            var response = m_forwarder.forward(context);

            context.setResponse(response);
        }
        finally {
            var runnable = context.getReleaseCallback();

            if (runnable != null)
                runnable.run();
        }
    }
}
