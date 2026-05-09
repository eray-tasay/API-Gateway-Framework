package com.eraytasay.wafflegateway.rpf.request.param;

public interface IQueryParameter {
    String getFirst();
    String getLast();
    Iterable<String> getAll();
    String getName();
    int count();
    boolean isEmpty();
}

