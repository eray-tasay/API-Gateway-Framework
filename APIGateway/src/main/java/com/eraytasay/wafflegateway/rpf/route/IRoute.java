package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.net.URI;
import java.util.List;

public interface IRoute {
    String getId();
    IRequestMatcher getMatcher();
    URI getTargetUri();
    List<IRequestFilter> getFilters();
}
