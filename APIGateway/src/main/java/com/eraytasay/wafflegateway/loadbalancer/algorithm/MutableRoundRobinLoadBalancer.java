package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MutableRoundRobinLoadBalancer implements IMutableServiceLoadBalancer {
    private final List<ServiceInstance> m_services;
    private int m_index;

    private MutableRoundRobinLoadBalancer()
    {
        m_services = new ArrayList<>();
    }

    public static MutableRoundRobinLoadBalancer of()
    {
        return new MutableRoundRobinLoadBalancer();
    }

    public static MutableRoundRobinLoadBalancer of(Iterable<ServiceInstance> services)
    {
        var object = new MutableRoundRobinLoadBalancer();

        services.forEach(object.m_services::add);

        return object;
    }

    public static MutableRoundRobinLoadBalancer of(ServiceInstance... services)
    {
        return MutableRoundRobinLoadBalancer.of(Arrays.asList(services));
    }

    /*
    * This method assumes that serviceInstance does not violate unique id, algorithm match, and service name match
    * constraints because these are already handled by both load balancer manager and service data source.
    * */
    @Override
    public synchronized void addService(ServiceInstance serviceInstance)
    {
        m_services.add(serviceInstance);
    }

    @Override
    public synchronized void deleteService(ServiceInstance serviceInstance)
    {
        m_services.removeIf(si -> si.getServiceId().equals(serviceInstance.getServiceId()));
    }

    @Override
    public synchronized ServiceInstance balance()
    {
        var res = m_services.get(m_index++);

        if (m_index >= m_services.size())
            m_index = 0;

        return res;
    }

    @Override
    public synchronized int getSize()
    {
        return m_services.size();
    }

    @Override
    public void release(ServiceInstance serviceInstance)
    {
        // Here is left empty on purpose because round-robin does not need any kind of releasing of resources.
    }
}
