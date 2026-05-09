package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.*;

public class LeastConnectionLoadBalancer implements IServiceLoadBalancer {
    private final List<ServiceInstance> m_services;
    private final Map<String, Integer> m_countersById;

    private LeastConnectionLoadBalancer()
    {
        m_services = new ArrayList<>();
        m_countersById = new HashMap<>();
    }

    public static LeastConnectionLoadBalancer of(Iterable<ServiceInstance> services)
    {
        var object = new LeastConnectionLoadBalancer();

        services.forEach(si -> add(object.m_services, object.m_countersById, si));

        return object;
    }

    public static LeastConnectionLoadBalancer of(ServiceInstance... services)
    {
        return of(Arrays.asList(services));
    }

    @Override
    public void release(ServiceInstance serviceInstance)
    {
        if (!m_countersById.containsKey(serviceInstance.getServiceId()))
            throw new NoSuchServiceException("There is no service instance with id %s".formatted(serviceInstance.getServiceId()));

        decrementCounter(serviceInstance.getServiceId());
    }

    @Override
    public ServiceInstance balance()
    {
        var idleOpt = m_services.stream().reduce((s1, s2) -> {
            var s1Counter = m_countersById.get(s1.getServiceId());
            var s2Counter = m_countersById.get(s2.getServiceId());

            return s1Counter < s2Counter ? s1 : s2;
        });

        if (idleOpt.isEmpty())
            return null;

        var idle = idleOpt.get();

        incrementCounter(idle.getServiceId());

        return idle;
    }

    @Override
    public int getSize()
    {
        return m_services.size();
    }

    private void incrementCounter(String serviceId)
    {
        // Merely changing the value associated with a key that an instance already contains is not a structural modification.
        m_countersById.merge(serviceId, 1, Integer::sum);
    }

    private void decrementCounter(String serviceId)
    {
        // Merely changing the value associated with a key that an instance already contains is not a structural modification.
        m_countersById.merge(serviceId, -1, Integer::sum);
    }

    /*
    * This method assumes that si does not violate unique id, algorithm match, and service name match constraints because
    * these are already handled by both load balancer manager and service data source.
    * */
    private static void add(List<ServiceInstance> services, Map<String, Integer> countersById, ServiceInstance si)
    {
        services.add(si);
        countersById.put(si.getServiceId(), 0);
    }
}
