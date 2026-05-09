package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

public interface IServiceLoadBalancer {
    ServiceInstance balance();
    void release(ServiceInstance serviceInstance);
    int getSize();
}
