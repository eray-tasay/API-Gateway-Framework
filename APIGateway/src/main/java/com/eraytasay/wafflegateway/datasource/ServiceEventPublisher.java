package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

public class ServiceEventPublisher {
    private final IServiceChangeListener m_listener;

    public ServiceEventPublisher(IServiceChangeListener listener)
    {
        m_listener = listener;
    }

    public void publishAdded(ServiceInstance instance)
    {
        m_listener.onServiceAdded(instance);
    }

    public void publishRemoved(ServiceInstance instance)
    {
        m_listener.onServiceRemoved(instance);
    }

    public void publishRefreshed(IServiceDataSource source)
    {
        m_listener.onRefreshed(source);
    }
}
