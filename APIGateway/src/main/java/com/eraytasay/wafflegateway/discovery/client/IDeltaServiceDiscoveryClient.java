package com.eraytasay.wafflegateway.discovery.client;

import org.springframework.http.ResponseEntity;

public interface IDeltaServiceDiscoveryClient<T> {
    ResponseEntity<T> fetchUpdates(long occurredAfter);
}
