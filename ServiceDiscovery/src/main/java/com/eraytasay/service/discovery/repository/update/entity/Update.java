package com.eraytasay.service.discovery.repository.update.entity;

import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;

public class Update {
    private final Type m_type;
    private final IServiceInstance m_serviceInstance;
    private final long m_occurredAt;

    public Update(Type type, IServiceInstance serviceInstance)
    {
        m_type = type;
        m_serviceInstance = serviceInstance;
        m_occurredAt = System.currentTimeMillis();
    }

    public Type getType()
    {
        return m_type;
    }

    public IServiceInstance getServiceInstance()
    {
        return m_serviceInstance;
    }

    public long getOccurredAt()
    {
        return m_occurredAt;
    }
}
