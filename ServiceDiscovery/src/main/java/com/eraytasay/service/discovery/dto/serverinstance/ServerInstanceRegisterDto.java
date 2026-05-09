package com.eraytasay.service.discovery.dto.serverinstance;

import com.eraytasay.service.discovery.repository.service.entity.LoadBalancing;

public class ServerInstanceRegisterDto {
    private String m_serviceName;
    private int m_port;
    private LoadBalancing m_algorithm;

    public String getServiceName()
    {
        return m_serviceName;
    }

    public void setServiceName(String serviceName)
    {
        m_serviceName = serviceName;
    }

    public LoadBalancing getLoadBalancingAlgorithm()
    {
        return m_algorithm;
    }

    public void setLoadBalancingAlgorithm(LoadBalancing algorithm)
    {
        m_algorithm = algorithm;
    }

    public int getPort()
    {
        return m_port;
    }

    public void setPort(int port)
    {
        m_port = port;
    }
}
