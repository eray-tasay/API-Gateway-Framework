package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.request.Request;

public class SimpleRequestProvider implements IRequestProvider {
    @Override
    public Request provide(RequestContext context)
    {
        var exchangeRequest = context.getExchangeRequest();
        var request = Request.of(exchangeRequest);
        var uri = context.getRoute().getTargetUri();

        if (uri.getScheme().equals("lb"))
            throw new UnsupportedOperationException("There is no configured load balancer.");

        return request;
    }
}
