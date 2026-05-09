package com.eraytasay.service.discovery.dto.serverinstance;

import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;

public class ServicesDto {
    private Iterable<IServiceInstance> m_services;
    private long m_snapshotTimestamp;

    public long getSnapshotTimestamp()
    {
        return m_snapshotTimestamp;
    }

    public void setSnapshotTimestamp(long snapshotTimestamp)
    {
        m_snapshotTimestamp = snapshotTimestamp;
    }

    public Iterable<IServiceInstance> getServices()
    {
        return m_services;
    }

    public void setServices(Iterable<IServiceInstance> services)
    {
        m_services = services;
    }
}
