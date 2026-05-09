package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.response.Response;

public interface IRequestForwarder {
    Response forward(RequestContext context);
}