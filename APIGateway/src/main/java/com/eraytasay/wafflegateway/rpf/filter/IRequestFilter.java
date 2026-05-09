package com.eraytasay.wafflegateway.rpf.filter;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.filter.chain.IFilterChain;

public interface IRequestFilter {
    void filter(RequestContext context, IFilterChain chain);
}
