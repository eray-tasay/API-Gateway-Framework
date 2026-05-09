package com.eraytasay.service.discovery.dto.serverinstance;

import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;
import com.eraytasay.service.discovery.repository.service.entity.LoadBalancing;

public class ServiceInstanceDto implements IServiceInstance {
    private String m_address;
    private int m_port;
    private String m_serviceName;
    private String m_serviceId;
    private LoadBalancing m_algorithm;
    private long m_lastHeartBeatTime;

    public ServiceInstanceDto(String serviceName, String address, int port, LoadBalancing algorithm)
    {
        m_serviceName = serviceName;
        m_address = address;
        m_port = port;
        m_algorithm = algorithm;
        m_serviceId = getServiceId(address, port);
        m_lastHeartBeatTime = System.currentTimeMillis();
    }

    public ServiceInstanceDto()
    {}

    @Override
    public String getAddress()
    {
        return m_address;
    }

    public void setAddress(String address)
    {
        m_address = address;
    }

    @Override
    public int getPort()
    {
        return m_port;
    }

    public void setPort(int port)
    {
        m_port = port;
    }

    @Override
    public String getServiceName()
    {
        return m_serviceName;
    }

    public void setServiceName(String serviceName)
    {
        m_serviceName = serviceName;
    }

    @Override
    public String getServiceId()
    {
        return m_serviceId;
    }

    public void setServiceId(String serviceId)
    {
        m_serviceId = serviceId;
    }

    @Override
    public LoadBalancing getLoadBalancingAlgorithm()
    {
        return m_algorithm;
    }

    public void setLoadBalancingAlgorithm(LoadBalancing algorithm)
    {
        m_algorithm = algorithm;
    }

    @Override
    public long getLastHeartBeatTime()
    {
        return m_lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(long lastHeartBeatTime)
    {
        m_lastHeartBeatTime = lastHeartBeatTime;
    }

    private static String getServiceId(String address, int port)
    {
        return String.format("%s:%d", address, port);
    }
}
