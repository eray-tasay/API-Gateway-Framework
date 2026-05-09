package com.eraytasay.wafflegateway.serviceistance;

public final class ServiceInstance {
    private final String m_address;
    private final int m_port;
    private final String m_serviceName;
    private final String m_serviceId;
    private final LoadBalancing m_loadBalancingAlgorithm;
    private final long m_lastHeartBeatTime;

    public ServiceInstance(String serviceName, String address, int port, LoadBalancing loadBalancingAlgorithm)
    {
        m_serviceName = serviceName;
        m_address = address;
        m_port = port;
        m_loadBalancingAlgorithm = loadBalancingAlgorithm;
        m_serviceId = getServiceId(address, port);
        m_lastHeartBeatTime = System.currentTimeMillis();
    }

    public String getAddress()
    {
        return m_address;
    }

    public int getPort()
    {
        return m_port;
    }

    public String getServiceName()
    {
        return m_serviceName;
    }

    public String getServiceId()
    {
        return m_serviceId;
    }

    public LoadBalancing getLoadBalancingAlgorithm()
    {
        return m_loadBalancingAlgorithm;
    }

    public long getLastHeartBeatTime()
    {
        return m_lastHeartBeatTime;
    }

    public boolean equals(Object other)
    {
        return other instanceof ServiceInstance si && si.m_serviceId.equals(m_serviceId);
    }

    public int hashCode()
    {
        return m_serviceId.hashCode();
    }

    public String toString()
    {
        return getServiceId();
    }

    private static String getServiceId(String address, int port)
    {
        return String.format("%s:%d", address, port);
    }
}
