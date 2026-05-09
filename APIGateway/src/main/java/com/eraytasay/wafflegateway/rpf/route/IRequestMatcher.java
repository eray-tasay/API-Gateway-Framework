package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public interface IRequestMatcher {
    boolean matches(IRequest request);
}