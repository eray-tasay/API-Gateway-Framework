package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.exception.ServiceAlreadyExistsException;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a mutable service data source. This class is completely thread-safe.
 */
public class MutableServiceDataSource implements IMutableServiceDataSource {
    private final ConcurrentHashMap<String, ServiceInstance> m_servicesById;

    private MutableServiceDataSource()
    {
        m_servicesById = new ConcurrentHashMap<>();
    }

    public static MutableServiceDataSource of()
    {
        return new MutableServiceDataSource();
    }

    public static MutableServiceDataSource of(Iterable<ServiceInstance> serviceInstances)
    {
        var object = new MutableServiceDataSource();

        serviceInstances.forEach(object::add);

        return object;
    }

    public static MutableServiceDataSource of(ServiceInstance... serviceInstances)
    {
        return of(Arrays.asList(serviceInstances));
    }

    public static MutableServiceDataSource of(IServiceDataSource dataSource)
    {
        var object = new MutableServiceDataSource();

        dataSource.getServices().forEach(object::add);

        return object;
    }

    @Override
    public void add(ServiceInstance serviceInstance)
    {
        if (m_servicesById.containsKey(serviceInstance.getServiceId()))
            throw new ServiceAlreadyExistsException("There is more than one service with id %s".formatted(serviceInstance.getServiceId()));

        m_servicesById.put(serviceInstance.getServiceId(), serviceInstance);
    }

    @Override
    public void delete(ServiceInstance serviceInstance)
    {
        var removed = m_servicesById.remove(serviceInstance.getServiceId());

        if (removed == null)
            throw new NoSuchServiceException("Service with id %s does not exist.".formatted(serviceInstance.getServiceId()));
    }

    @Override
    public void refresh(IServiceDataSource dataSource)
    {
        m_servicesById.clear();

        dataSource.getServices().forEach(this::add);
    }

    @Override
    public Iterable<ServiceInstance> getServices()
    {
        return new ArrayList<>(m_servicesById.values());
    }

    @Override
    public Optional<ServiceInstance> getService(String serviceId)
    {
        var si = m_servicesById.get(serviceId);

        return si == null ? Optional.empty() : Optional.of(si);
    }

    @Override
    public int size()
    {
        return m_servicesById.size();
    }
}
