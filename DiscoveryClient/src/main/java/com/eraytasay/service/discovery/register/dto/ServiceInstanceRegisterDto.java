package com.eraytasay.service.discovery.register.dto;

public class ServiceInstanceRegisterDto {
    private String m_serviceName;
    private LoadBalancing m_algorithm;
    private int m_port;

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
