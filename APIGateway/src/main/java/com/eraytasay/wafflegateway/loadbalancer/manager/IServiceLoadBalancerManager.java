package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.loadbalancer.algorithm.IServiceLoadBalancer;

/**
 * This interface is designed to retrieve service load balancers. It can be used to organize all service load balancers
 * in the system so that they can be retrieved during load balancing.
 */
public interface IServiceLoadBalancerManager {
    IServiceLoadBalancer getServiceLoadBalancer(String serviceName);
    Iterable<String> getServiceNames();
}
