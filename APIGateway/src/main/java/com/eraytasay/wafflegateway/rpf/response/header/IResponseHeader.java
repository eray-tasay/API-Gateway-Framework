package com.eraytasay.wafflegateway.rpf.response.header;

public interface IResponseHeader {
    String getFirst();
    String getLast();
    Iterable<String> getAll();
    String getName();
    int count();
    boolean isEmpty();
}
