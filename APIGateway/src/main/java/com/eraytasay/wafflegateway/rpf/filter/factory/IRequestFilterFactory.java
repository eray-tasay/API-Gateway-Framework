package com.eraytasay.wafflegateway.rpf.filter.factory;

import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;

import java.util.List;

public interface IRequestFilterFactory {
    String name();
    IRequestFilter create(List<String> args);
}
