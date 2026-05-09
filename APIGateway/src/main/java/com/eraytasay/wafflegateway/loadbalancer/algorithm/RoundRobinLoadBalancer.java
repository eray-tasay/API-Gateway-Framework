package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundRobinLoadBalancer implements IServiceLoadBalancer {
    private final List<ServiceInstance> m_services;
    private volatile int m_index;

    private RoundRobinLoadBalancer()
    {
        m_services = new ArrayList<>();
    }

    public static RoundRobinLoadBalancer of(Iterable<ServiceInstance> services)
    {
        var object = new RoundRobinLoadBalancer();

        services.forEach(object.m_services::add);

        return object;
    }

    public static RoundRobinLoadBalancer of(ServiceInstance... services)
    {
        return RoundRobinLoadBalancer.of(Arrays.asList(services));
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
    public int getSize()
    {
        return m_services.size();
    }

    @Override
    public void release(ServiceInstance serviceInstance)
    {
        // Here is left empty on purpose because round-robin does not need any kind of releasing of resources.
    }
}
