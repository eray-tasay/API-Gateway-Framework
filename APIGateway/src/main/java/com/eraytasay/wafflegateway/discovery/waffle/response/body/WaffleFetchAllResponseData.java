package com.eraytasay.wafflegateway.discovery.waffle.response.body;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.util.List;

public class WaffleFetchAllResponseData {
    private List<ServiceInstance> m_services;
    private long m_snapshotTimestamp;

    public List<ServiceInstance> getServices()
    {
        return m_services;
    }

    public void setServices(List<ServiceInstance> services)
    {
        m_services = services;
    }

    public long getSnapshotTimestamp()
    {
        return m_snapshotTimestamp;
    }

    public void setSnapshotTimestamp(long snapshotTimestamp)
    {
        m_snapshotTimestamp = snapshotTimestamp;
    }
}
