package com.eraytasay.service.discovery.repository.service;

import com.eraytasay.service.discovery.exception.NoSuchServiceException;
import com.eraytasay.service.discovery.exception.ServiceAlreadyExistsException;
import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Repository
public class ServiceRepository {
    private final ConcurrentHashMap<String, ServiceInstance> m_servicesById;

    public ServiceRepository()
    {
        m_servicesById = new ConcurrentHashMap<>();
    }

    /**
     * Adds the given serviceInstance. It copies serviceInstance before adding. This method performs on O(1) time complexity.
     *
     * @throws ServiceAlreadyExistsException if {@code serviceInstance} violates id uniqueness.
     */
    public IServiceInstance save(IServiceInstance serviceInstance)
    {
        if (m_servicesById.containsKey(serviceInstance.getServiceId()))
            throw new ServiceAlreadyExistsException("Service with id %s already exists.".formatted(serviceInstance.getServiceId()));

        var serviceInstanceEntity = new ServiceInstance(serviceInstance.getServiceName(), serviceInstance.getAddress(),
                serviceInstance.getPort(), serviceInstance.getLoadBalancingAlgorithm());

        m_servicesById.put(serviceInstanceEntity.getServiceId(), serviceInstanceEntity);

        return serviceInstanceEntity;
    }

    /**
     * Retrieves the service instance with the given service id. This method performs on O(1) time complexity.
     */
    public Optional<IServiceInstance> findByServiceId(String serviceId)
    {
        var res = m_servicesById.get(serviceId);

        return res == null ? Optional.empty() : Optional.of(res);
    }

    /**
     * Updates the last heart beat time of service instance with id {@code serviceId}. This method performs on O(1) time complexity.
     *
     * @throws NoSuchServiceException if there is no service with id {@code serviceId}
     */
    public void updateLastHeartBeatTime(String serviceId)
    {
        var si = m_servicesById.get(serviceId);

        if (si == null)
            throw new NoSuchServiceException("Service with id %s does not exist.".formatted(serviceId));

        si.setLastHeartBeatTime();
    }

    /**
     * Removes the service instance with id {@code serviceId}. This method performs on O(1) time complexity.
     *
     * @throws NoSuchServiceException if there is no service with id {@code serviceId}
     */
    public IServiceInstance deleteById(String serviceId)
    {
        var removed = m_servicesById.remove(serviceId);

        if (removed == null)
            throw new NoSuchServiceException("Service with id %s does not exist.".formatted(serviceId));

        return removed;
    }

    /**
     * Removes all services that satisfy given condition. This method performs on O(n) time complexity.
     */
    public void deleteByCondition(Predicate<IServiceInstance> callback)
    {
        m_servicesById.entrySet().removeIf(entry -> callback.test(entry.getValue()));
    }

    /**
     * Gets all services.
     */
    public Iterable<IServiceInstance> findAll()
    {
        return new ArrayList<>(m_servicesById.values());
    }

    public int count()
    {
        return m_servicesById.size();
    }
}
