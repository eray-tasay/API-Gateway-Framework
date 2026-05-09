package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

/**
 * This interface is designed to both retrieve and update service load balancers.
 */
public interface IMutableServiceLoadBalancerManager extends IServiceLoadBalancerManager {
    void addService(ServiceInstance serviceInstance);
    void deleteService(ServiceInstance serviceInstance);
    void refresh(IServiceDataSource serviceDataSource);
}
