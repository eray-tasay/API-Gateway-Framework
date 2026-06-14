package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.exception.AlgorithmMismatchException;
import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.*;
import com.eraytasay.wafflegateway.serviceistance.LoadBalancing;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is a mutable service load balancer manager implementation.
 */
public class MutableServiceLoadBalancerManager implements IMutableServiceLoadBalancerManager {
    private volatile ConcurrentHashMap<String, IMutableServiceLoadBalancer> m_loadBalancersByServiceName;

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
        m_loadBalancersByServiceName = createNewServiceLoadBalancers(dataSource);
    }

    private ConcurrentHashMap<String, IMutableServiceLoadBalancer> createNewServiceLoadBalancers(IServiceDataSource dataSource)
    {
        ConcurrentHashMap<String, IMutableServiceLoadBalancer> res = new ConcurrentHashMap<>();

        dataSource.getServices().forEach(si -> {
            var serviceName = si.getServiceName();

            if (si.getLoadBalancingAlgorithm() == LoadBalancing.LEAST_CONNECTION) {
                var loadBalancer = res.get(serviceName);

                MutableLeastConnectionLoadBalancer leastConnectionLoadBalancer;

                if (loadBalancer == null) {
                    leastConnectionLoadBalancer = MutableLeastConnectionLoadBalancer.of();
                    res.put(serviceName, leastConnectionLoadBalancer);
                }
                else {
                    checkAlgorithm(LoadBalancing.LEAST_CONNECTION, loadBalancer);
                    leastConnectionLoadBalancer = (MutableLeastConnectionLoadBalancer)loadBalancer;
                }

                leastConnectionLoadBalancer.addServiceWithLoad(si, getServiceLoad(si));
            }
            else {
                var loadBalancer = res.get(serviceName);

                if (loadBalancer == null) {
                    loadBalancer = MutableRoundRobinLoadBalancer.of();
                    res.put(serviceName, loadBalancer);
                }
                else
                    checkAlgorithm(LoadBalancing.ROUND_ROBIN, loadBalancer);

                loadBalancer.addService(si);
            }
        });

        return res;
    }

    private int getServiceLoad(ServiceInstance si)
    {
        var balancer = m_loadBalancersByServiceName.get(si.getServiceName());

        if (balancer == null)
            return 0;

        if (balancer instanceof MutableRoundRobinLoadBalancer)
            return 0;

        MutableLeastConnectionLoadBalancer leastConnectionLoadBalancer = (MutableLeastConnectionLoadBalancer)balancer;

        try {
            return leastConnectionLoadBalancer.getLoad(si);
        }
        catch (NoSuchServiceException ignore) {
            return 0;
        }
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
