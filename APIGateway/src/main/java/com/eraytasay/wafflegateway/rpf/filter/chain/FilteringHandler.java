package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.request.Request;
import com.eraytasay.wafflegateway.rpf.response.Response;
import com.eraytasay.wafflegateway.rpf.route.IRoute;
import org.springframework.stereotype.Component;

@Component
public class FilteringHandler {
    private final IRequestForwarder m_forwarder;

    public FilteringHandler(IRequestForwarder forwarder)
    {
        m_forwarder = forwarder;
    }

    public Response handle(IRoute route, Request request)
    {
        var context = new RequestContext(request, route);
        var chain = new DefaultFilterChain(route.getFilters(), m_forwarder);

        chain.next(context);

        return context.getResponse();
    }
}
