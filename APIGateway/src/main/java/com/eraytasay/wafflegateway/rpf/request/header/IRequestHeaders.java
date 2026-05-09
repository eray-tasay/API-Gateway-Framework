package com.eraytasay.wafflegateway.rpf.request.header;

public interface IRequestHeaders {
    IRequestHeader get(String name);
    int count();
    boolean isEmpty();
    Iterable<String> getNames();
}
