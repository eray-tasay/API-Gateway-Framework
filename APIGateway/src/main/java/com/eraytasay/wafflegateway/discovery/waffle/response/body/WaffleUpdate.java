package com.eraytasay.wafflegateway.discovery.waffle.response.body;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

public class WaffleUpdate {
    private Type m_type;
    private ServiceInstance m_serviceInstance;
    private long m_occurredAt;

    public Type getType()
    {
        return m_type;
    }

    public void setType(Type type)
    {
        m_type = type;
    }

    public ServiceInstance getServiceInstance()
    {
        return m_serviceInstance;
    }

    public void setServiceInstance(ServiceInstance serviceInstance)
    {
        m_serviceInstance = serviceInstance;
    }

    public long getOccurredAt()
    {
        return m_occurredAt;
    }

    public void setOccurredAt(long occurredAt)
    {
        m_occurredAt = occurredAt;
    }
}
