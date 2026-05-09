package com.eraytasay.wafflegateway.discovery.client;

import org.springframework.http.ResponseEntity;

public interface IFetchAllServiceDiscoveryClient<T> {
    ResponseEntity<T> fetchAll();
}
