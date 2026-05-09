package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.exception.AlgorithmMismatchException;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.IMutableServiceLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.IServiceLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableLeastConnectionLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableRoundRobinLoadBalancer;
import com.eraytasay.wafflegateway.serviceistance.LoadBalancing;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is a mutable service load balancer manager implementation.
 */
public class MutableServiceLoadBalancerManager implements IMutableServiceLoadBalancerManager {
    private final ConcurrentHashMap<String, IMutableServiceLoadBalancer> m_loadBalancersByServiceName;

    public static MutableServiceLoadBalancerManager of()
    {
        return new MutableServiceLoadBalancerManager();
    }

    public static MutableServiceLoadBalancerManager of(IServiceDataSource dataSource)
    {
        var object = new MutableServiceLoadBalancerManager();

        init(object, dataSource.getServices());

        return object;
    }

    private MutableServiceLoadBalancerManager()
    {
        m_loadBalancersByServiceName = new ConcurrentHashMap<>();
    }

    @Override
    public void addService(ServiceInstance s)
    {
        var serviceName = s.getServiceName();
        var balancer = m_loadBalancersByServiceName.get(serviceName);

        if (balancer == null) {
            balancer = createServiceLoadBalancer(s.getLoadBalancingAlgorithm());
            m_loadBalancersByServiceName.put(serviceName, balancer);
        }
        else
            checkAlgorithm(s.getLoadBalancingAlgorithm(), balancer);

        balancer.addService(s);
    }

    @Override
    public void deleteService(ServiceInstance s)
    {
        var serviceName = s.getServiceName();
        var balancer = m_loadBalancersByServiceName.get(serviceName);

        if (balancer == null)
            return;

        balancer.deleteService(s);

        if (balancer.getSize() == 0)
            m_loadBalancersByServiceName.remove(serviceName);
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

    @Override
    public void refresh(IServiceDataSource dataSource)
    {
        m_loadBalancersByServiceName.clear();

        init(this, dataSource.getServices());
    }

    private static IMutableServiceLoadBalancer createServiceLoadBalancer(LoadBalancing algorithm)
    {
       return switch (algorithm) {
            case ROUND_ROBIN -> MutableRoundRobinLoadBalancer.of();
            case LEAST_CONNECTION -> MutableLeastConnectionLoadBalancer.of();
        };
    }

    private static void init(MutableServiceLoadBalancerManager object, Iterable<ServiceInstance> services)
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
