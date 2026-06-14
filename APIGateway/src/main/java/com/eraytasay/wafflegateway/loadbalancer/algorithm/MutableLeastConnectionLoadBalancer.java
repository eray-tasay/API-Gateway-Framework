package com.eraytasay.wafflegateway.loadbalancer.algorithm;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MutableLeastConnectionLoadBalancer implements IMutableServiceLoadBalancer {
    private final List<ServiceInstance> m_services;
    private final Map<String, Integer> m_loadsById;
    private final Lock m_readLock;
    private final Lock m_writeLock;

    private MutableLeastConnectionLoadBalancer()
    {
        m_services = new ArrayList<>();
        m_loadsById = new HashMap<>();

        var reentrantReadWriteLock = new ReentrantReadWriteLock();

        m_readLock = reentrantReadWriteLock.readLock();
        m_writeLock = reentrantReadWriteLock.writeLock();
    }

    public static MutableLeastConnectionLoadBalancer of(Iterable<ServiceInstance> services)
    {
        var object = new MutableLeastConnectionLoadBalancer();

        services.forEach(si -> add(object.m_services, object.m_loadsById, si));

        return object;
    }

    public static MutableLeastConnectionLoadBalancer of(ServiceInstance... services)
    {
        return of(Arrays.asList(services));
    }

    /*
     * This method assumes that serviceInstance does not violate unique id, algorithm match, and service name match
     * constraints because these are already handled by both load balancer manager and service data source.
     * */
    @Override
    public void addService(ServiceInstance serviceInstance)
    {
        m_writeLock.lock();

        try {
            m_services.add(serviceInstance);
            m_loadsById.put(serviceInstance.getServiceId(), 0);
        }
        finally {
            m_writeLock.unlock();
        }
    }

    public void addServiceWithLoad(ServiceInstance serviceInstance, int count)
    {
        m_writeLock.lock();

        try {
            m_services.add(serviceInstance);
            m_loadsById.put(serviceInstance.getServiceId(), count);
        }
        finally {
            m_writeLock.unlock();
        }
    }

    @Override
    public void deleteService(ServiceInstance serviceInstance)
    {
        m_writeLock.lock();

        try {
            m_services.remove(serviceInstance);
        }
        finally {
            m_writeLock.unlock();
        }
    }

    @Override
    public void release(ServiceInstance serviceInstance)
    {
        m_readLock.lock();

        try {
            if (!m_loadsById.containsKey(serviceInstance.getServiceId()))
                throw new NoSuchServiceException("There is no service instance with id %s".formatted(serviceInstance.getServiceId()));

            decrementLoad(serviceInstance.getServiceId());
        }
        finally {
            m_readLock.unlock();
        }
    }

    @Override
    public ServiceInstance balance()
    {
        ServiceInstance idle;

        m_readLock.lock();

        try {
            var idleOpt = m_services.stream().reduce((s1, s2) -> {
                var s1Counter = m_loadsById.get(s1.getServiceId());
                var s2Counter = m_loadsById.get(s2.getServiceId());

                return s1Counter < s2Counter ? s1 : s2;
            });

            if (idleOpt.isEmpty())
                return null;

            idle = idleOpt.get();
            incrementLoad(idle.getServiceId());
        }
        finally {
            m_readLock.unlock();
        }

        return idle;
    }

    @Override
    public int getSize()
    {
        m_readLock.lock();

        try {
            return m_services.size();
        }
        finally {
            m_readLock.unlock();
        }
    }

    public int getLoad(ServiceInstance serviceInstance)
    {
        m_readLock.lock();

        try {
            if (!m_loadsById.containsKey(serviceInstance.getServiceId()))
                throw new NoSuchServiceException("There is no service instance with id %s".formatted(serviceInstance.getServiceId()));

            return m_loadsById.get(serviceInstance.getServiceId());
        }
        finally {
            m_readLock.unlock();
        }
    }

    private void incrementLoad(String serviceId)
    {
        // Merely changing the value associated with a key that an instance already contains is not a structural modification.
        m_loadsById.merge(serviceId, 1, Integer::sum);
    }

    private void decrementLoad(String serviceId)
    {
        // Merely changing the value associated with a key that an instance already contains is not a structural modification.
        m_loadsById.merge(serviceId, -1, Integer::sum);
    }

    /*
     * This method assumes that si does not violate unique id, algorithm match, and service name match constraints because
     * these are already handled by both load balancer manager and service data source.
     * */
    private static void add(List<ServiceInstance> services, Map<String, Integer> loadsById, ServiceInstance si)
    {
        services.add(si);
        loadsById.put(si.getServiceId(), 0);
    }
}
