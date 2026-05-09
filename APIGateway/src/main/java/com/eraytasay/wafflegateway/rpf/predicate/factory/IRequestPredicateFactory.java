package com.eraytasay.wafflegateway.rpf.predicate.factory;

import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;

import java.util.List;

public interface IRequestPredicateFactory {
    String name();
    IRequestPredicate create(List<String> args);
}
