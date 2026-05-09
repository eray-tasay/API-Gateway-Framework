package com.eraytasay.service.discovery.register;

import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.exception.ServiceRegistrationException;
import com.eraytasay.service.discovery.register.dto.LoadBalancing;
import com.eraytasay.service.discovery.register.dto.ServiceInstanceRegisterDto;
import com.eraytasay.service.discovery.register.handler.IRegistrationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class ServiceRegistrar {
    private static final Logger log = LoggerFactory.getLogger(ServiceRegistrar.class);

    private final String m_serviceDiscoveryUrl;
    private final String m_serviceName;
    private final String m_registerPath;
    private final LoadBalancing m_algorithm;
    private final int m_servicePort;
    private final RestClient m_restClient;
    private final IRegistrationSuccessHandler m_successHandler;

    private ServiceRegistrar(Builder builder)
    {
        m_serviceDiscoveryUrl = builder.m_serviceDiscoveryUrl;
        m_serviceName = builder.m_serviceName;
        m_registerPath = builder.m_registerPath;
        m_algorithm = builder.m_algorithm;
        m_servicePort = builder.m_servicePort;
        m_restClient = builder.m_restClient;
        m_successHandler = builder.m_successHandler;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder {
        private String m_serviceDiscoveryUrl;
        private String m_serviceName;
        private String m_registerPath;
        private LoadBalancing m_algorithm;
        private int m_servicePort;
        private RestClient m_restClient;
        private IRegistrationSuccessHandler m_successHandler;

        private Builder()
        {}

        public Builder serviceDiscoveryUrl(String url)
        {
            m_serviceDiscoveryUrl = url;
            return this;
        }

        public Builder serviceName(String name)
        {
            m_serviceName = name;
            return this;
        }

        public Builder registerPath(String path)
        {
            m_registerPath = path;
            return this;
        }

        public Builder algorithm(LoadBalancing algorithm)
        {
            m_algorithm = algorithm;
            return this;
        }

        public Builder servicePort(int port)
        {
            m_servicePort = port;
            return this;
        }

        public Builder restClient(RestClient restClient)
        {
            m_restClient = restClient;
            return this;
        }

        public Builder successHandler(IRegistrationSuccessHandler successHandler)
        {
            m_successHandler = successHandler;
            return this;
        }

        public ServiceRegistrar build()
        {
            if (m_serviceDiscoveryUrl == null)
                throw new IllegalStateException("Service discovery URL cannot be null");
            if (m_serviceName == null)
                throw new IllegalStateException("Service name cannot be null");
            if (m_registerPath == null)
                throw new IllegalStateException("Register path cannot be null");
            if (m_algorithm == null)
                throw new IllegalStateException("Algorithm cannot be null");
            if (m_servicePort <= 0 || m_servicePort > 65535)
                throw new IllegalStateException("Port must be between 1 and 65535");
            if (m_restClient == null)
                throw new IllegalStateException("RestClient cannot be null");

            return new ServiceRegistrar(this);
        }
    }

    public String getServiceDiscoveryUrl()
    {
        return m_serviceDiscoveryUrl;
    }

    public String getRegisterPath()
    {
        return m_registerPath;
    }

    public String getFullUrl()
    {
        return "%s%s".formatted(m_serviceDiscoveryUrl, m_registerPath);
    }

    public int getServicePort()
    {
        return m_servicePort;
    }

    public LoadBalancing getAlgorithm()
    {
        return m_algorithm;
    }

    public String getServiceName()
    {
        return m_serviceName;
    }

    public RestClient getRestClient()
    {
        return m_restClient;
    }

    public IRegistrationSuccessHandler getSuccessHandler()
    {
        return m_successHandler;
    }

    public ResponseDto<?> register()
    {
        var body = new ServiceInstanceRegisterDto();
        var fullUrl = getFullUrl();

        body.setServiceName(m_serviceName);
        body.setPort(m_servicePort);
        body.setLoadBalancingAlgorithm(m_algorithm);

        ResponseDto<?> response;

        try {
            response = m_restClient.post()
                    .uri(fullUrl)
                    .body(body)
                    .retrieve()
                    .body(ResponseDto.class);

            m_successHandler.onRegistrationSuccess();
        }
        catch (RestClientException ex) {
            throw new ServiceRegistrationException("Service %s cannot be registered with %s.".formatted(m_serviceName, fullUrl), ex);
        }

        log.info("Service {} is registered with {}.", m_serviceName, fullUrl);

        return response;
    }
}
