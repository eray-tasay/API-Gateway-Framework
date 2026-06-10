package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.request.Request;

public interface IRequestProvider {
    Request provide(RequestContext context);
}
