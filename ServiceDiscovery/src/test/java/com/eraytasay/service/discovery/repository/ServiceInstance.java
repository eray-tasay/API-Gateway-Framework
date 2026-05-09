package com.eraytasay.service.discovery.repository;

import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;
import com.eraytasay.service.discovery.repository.service.entity.LoadBalancing;

class ServiceInstance implements IServiceInstance {
    private final String m_address;
    private final int m_port;
    private final String m_serviceName;
    private final String m_serviceId;
    private final LoadBalancing m_algorithm;
    private volatile long m_lastHeartBeatTime;

    public ServiceInstance(String serviceName, String address, int port, LoadBalancing algorithm)
    {
        m_serviceName = serviceName;
        m_address = address;
        m_port = port;
        m_algorithm = algorithm;
        m_serviceId = getServiceId(address, port);
        m_lastHeartBeatTime = System.currentTimeMillis();
    }

    @Override
    public String getAddress()
    {
        return m_address;
    }

    @Override
    public int getPort()
    {
        return m_port;
    }

    @Override
    public String getServiceName()
    {
        return m_serviceName;
    }

    @Override
    public String getServiceId()
    {
        return m_serviceId;
    }

    @Override
    public LoadBalancing getLoadBalancingAlgorithm()
    {
        return m_algorithm;
    }

    @Override
    public long getLastHeartBeatTime()
    {
        return m_lastHeartBeatTime;
    }

    public void setLastHeartBeatTime()
    {
        m_lastHeartBeatTime = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof ServiceInstance si && si.m_serviceId.equals(m_serviceId);
    }

    @Override
    public int hashCode()
    {
        return m_serviceId.hashCode();
    }

    @Override
    public String toString()
    {
        return getServiceId();
    }

    private static String getServiceId(String address, int port)
    {
        return String.format("%s:%d", address, port);
    }
}

