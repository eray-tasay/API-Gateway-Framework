package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.exception.AlgorithmMismatchException;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.IMutableServiceLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.IServiceLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableLeastConnectionLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableRoundRobinLoadBalancer;
import com.eraytasay.wafflegateway.serviceistance.LoadBalancing;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.*;

/**
 * This class is an immutable service load balancer manager implementation.
 */
public class ServiceLoadBalancerManager implements IServiceLoadBalancerManager {
    private final Map<String, IMutableServiceLoadBalancer> m_loadBalancersByServiceName;

    private ServiceLoadBalancerManager()
    {
        m_loadBalancersByServiceName = new HashMap<>();
    }

    public static ServiceLoadBalancerManager of(IServiceDataSource dataSource)
    {
        var object = new ServiceLoadBalancerManager();

        init(object, dataSource.getServices());

        return object;
    }

    @Override
    public IServiceLoadBalancer getServiceLoadBalancer(String serviceName)
    {
        return m_loadBalancersByServiceName.get(serviceName);
    }

    @Override
    public Iterable<String> getServiceNames()
    {
        return new ArrayList<>(m_loadBalancersByServiceName.keySet());
    }

    private static IMutableServiceLoadBalancer createServiceLoadBalancer(LoadBalancing algorithm)
    {
        return switch (algorithm) {
            case ROUND_ROBIN -> MutableRoundRobinLoadBalancer.of();
            case LEAST_CONNECTION -> MutableLeastConnectionLoadBalancer.of();
        };
    }

    private static void init(ServiceLoadBalancerManager object, Iterable<ServiceInstance> services)
    {
        services.forEach(si -> {
            var serviceName = si.getServiceName();
            var loadBalancer = object.m_loadBalancersByServiceName.get(serviceName);

            if (loadBalancer == null) {
                loadBalancer = createServiceLoadBalancer(si.getLoadBalancingAlgorithm());
                object.m_loadBalancersByServiceName.put(serviceName, loadBalancer);
            }
            else
                checkAlgorithm(si.getLoadBalancingAlgorithm(), loadBalancer);

            loadBalancer.addService(si);
        });
    }

    private static void checkAlgorithm(LoadBalancing algorithm, IMutableServiceLoadBalancer loadBalancer)
    {
        switch (algorithm) {
            case ROUND_ROBIN -> {
                if (!(loadBalancer instanceof MutableRoundRobinLoadBalancer))
                    throw new AlgorithmMismatchException("All service instances of a single service must use the same algorithm.");
            }
            case LEAST_CONNECTION -> {
                if (!(loadBalancer instanceof MutableLeastConnectionLoadBalancer))
                    throw new AlgorithmMismatchException("All service instances of a single service must use the same algorithm.");
            }
        }
    }
}
