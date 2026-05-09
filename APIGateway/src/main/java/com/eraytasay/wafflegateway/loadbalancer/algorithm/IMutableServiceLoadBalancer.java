package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

public interface IMutableServiceLoadBalancer extends IServiceLoadBalancer {
    void addService(ServiceInstance serviceInstance);
    void deleteService(ServiceInstance serviceInstance);
}
