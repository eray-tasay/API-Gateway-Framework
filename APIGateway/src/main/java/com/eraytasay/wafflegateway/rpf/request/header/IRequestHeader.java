package com.eraytasay.wafflegateway.rpf.request.header;

public interface IRequestHeader {
    String getFirst();
    String getLast();
    Iterable<String> getAll();
    String getName();
    int count();
    boolean isEmpty();
}
