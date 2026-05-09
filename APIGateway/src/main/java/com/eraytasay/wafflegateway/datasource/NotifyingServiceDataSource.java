package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.Arrays;
import java.util.Optional;

/**
 * This is a data source that notifies its changes to a IServiceChangeListener. This class is completely thread-safe.
 */
public class NotifyingServiceDataSource implements IMutableServiceDataSource {
    private final IMutableServiceDataSource m_dataSource;
    private volatile ServiceEventPublisher m_publisher;

    private NotifyingServiceDataSource(IMutableServiceDataSource dataSource)
    {
        m_dataSource = dataSource;
    }

    public static NotifyingServiceDataSource of()
    {
        return new NotifyingServiceDataSource(MutableServiceDataSource.of());
    }

    public static NotifyingServiceDataSource of(Iterable<ServiceInstance> serviceInstances)
    {
        return new NotifyingServiceDataSource(MutableServiceDataSource.of(serviceInstances));
    }

    public static NotifyingServiceDataSource of(ServiceInstance... serviceInstances)
    {
        return of(Arrays.asList(serviceInstances));
    }

    public static NotifyingServiceDataSource of(IServiceDataSource dataSource)
    {
        return new NotifyingServiceDataSource(MutableServiceDataSource.of(dataSource));
    }

    public void subscribe(IServiceChangeListener listener)
    {
        m_publisher = new ServiceEventPublisher(listener);
    }

    public void unsubscribe()
    {
        m_publisher = null;
    }

    @Override
    public void add(ServiceInstance serviceInstance)
    {
        m_dataSource.add(serviceInstance);
        m_publisher.publishAdded(serviceInstance);
    }

    @Override
    public void delete(ServiceInstance serviceInstance)
    {
        m_dataSource.delete(serviceInstance);
        m_publisher.publishRemoved(serviceInstance);
    }

    @Override
    public void refresh(IServiceDataSource dataSource)
    {
        m_dataSource.refresh(dataSource);
        m_publisher.publishRefreshed(m_dataSource);
    }

    @Override
    public Iterable<ServiceInstance> getServices()
    {
        return m_dataSource.getServices();
    }

    @Override
    public Optional<ServiceInstance> getService(String serviceId)
    {
        return m_dataSource.getService(serviceId);
    }

    @Override
    public int size()
    {
        return m_dataSource.size();
    }
}
