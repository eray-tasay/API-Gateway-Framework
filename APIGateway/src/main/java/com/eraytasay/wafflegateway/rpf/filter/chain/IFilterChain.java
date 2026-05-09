package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;

public interface IFilterChain {
    void next(RequestContext context);
}