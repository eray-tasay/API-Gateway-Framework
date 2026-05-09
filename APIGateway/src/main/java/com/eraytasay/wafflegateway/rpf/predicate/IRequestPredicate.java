package com.eraytasay.wafflegateway.rpf.predicate;

import com.eraytasay.wafflegateway.rpf.request.IRequest;

public interface IRequestPredicate {
    boolean test(IRequest request);
}
