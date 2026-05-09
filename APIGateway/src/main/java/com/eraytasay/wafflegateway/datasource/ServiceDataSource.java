package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.exception.ServiceAlreadyExistsException;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.*;

/**
 * This class represents an immutable service data source. This class is completely thread-safe.
 */
public class ServiceDataSource implements IServiceDataSource {
    private final Map<String, ServiceInstance> m_servicesById;

    public static ServiceDataSource of(Iterable<ServiceInstance> services)
    {
        var object = new ServiceDataSource();

        services.forEach(si -> {
            var oldResult = object.m_servicesById.put(si.getServiceId(), si);

            if (oldResult != null)
                throw new ServiceAlreadyExistsException("There is more than one service with id %s".formatted(oldResult.getServiceId()));
        });

        return object;
    }

    public static ServiceDataSource of(ServiceInstance... services)
    {
        return of(Arrays.asList(services));
    }

    private ServiceDataSource()
    {
        m_servicesById = new HashMap<>();
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
