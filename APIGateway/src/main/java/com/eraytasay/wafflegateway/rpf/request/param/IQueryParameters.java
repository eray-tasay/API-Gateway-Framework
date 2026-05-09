package com.eraytasay.wafflegateway.rpf.request.param;

public interface IQueryParameters {
    IQueryParameter get(String name);
    int count();
    boolean isEmpty();
    Iterable<String> getNames();
}
