package com.eraytasay.wafflegateway.rpf.response.header;

public interface IResponseHeaders {
    IResponseHeader get(String name);
    int count();
    boolean isEmpty();
    Iterable<String> getNames();
}
