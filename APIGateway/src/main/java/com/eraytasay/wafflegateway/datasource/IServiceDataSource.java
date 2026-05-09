package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.Optional;

/**
 * This interface is designed to get stored service instances.
 */
public interface IServiceDataSource {
    Iterable<ServiceInstance> getServices();
    Optional<ServiceInstance> getService(String serviceId);
    int size();
}
