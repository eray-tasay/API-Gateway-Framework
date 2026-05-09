package com.eraytasay.wafflegateway.discovery.waffle.response.body;

import java.util.List;

public class WaffleDeltaResponseData {
    private List<WaffleUpdate> m_updates;
    private long m_snapshotTimestamp;
    private long m_deltaVersion;
    private int m_numberOfServices;

    public long getSnapshotTimestamp()
    {
        return m_snapshotTimestamp;
    }

    public void setSnapshotTimestamp(long snapshotTimestamp)
    {
        m_snapshotTimestamp = snapshotTimestamp;
    }

    public List<WaffleUpdate> getUpdates()
    {
        return m_updates;
    }

    public void setUpdates(List<WaffleUpdate> updates)
    {
        m_updates = updates;
    }

    public long getDeltaVersion()
    {
        return m_deltaVersion;
    }

    public void setDeltaVersion(long deltaVersion)
    {
        m_deltaVersion = deltaVersion;
    }

    public int getNumberOfServices()
    {
        return m_numberOfServices;
    }

    public void setNumberOfServices(int numberOfServices)
    {
        m_numberOfServices = numberOfServices;
    }
}
