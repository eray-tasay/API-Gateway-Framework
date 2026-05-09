package com.eraytasay.service.discovery.dto.heartbeating;

public class HeartBeatingRequestDto {
    private String m_serviceName;
    private int m_port;

    public String getServiceName()
    {
        return m_serviceName;
    }

    public void setServiceName(String serviceName)
    {
        m_serviceName = serviceName;
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
