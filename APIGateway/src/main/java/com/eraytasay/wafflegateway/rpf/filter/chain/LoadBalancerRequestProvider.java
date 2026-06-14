package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.loadbalancer.manager.IServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.request.Request;

/*
* This class provides the request sent to the resolved service.
* */
public class LoadBalancerRequestProvider implements IRequestProvider {
    private final IServiceLoadBalancerManager m_loadBalancerManager;

    public LoadBalancerRequestProvider(IServiceLoadBalancerManager loadBalancerManager)
    {
        m_loadBalancerManager = loadBalancerManager;
    }

    @Override
    public Request provide(RequestContext context)
    {
        var exchangeRequest = context.getExchangeRequest();
        var request = Request.of(exchangeRequest);
        var uri = context.getRoute().getTargetUri();

        if (uri.getScheme().equals("lb")) {
            var serviceName = uri.getHost();
            var loadBalancer = m_loadBalancerManager.getServiceLoadBalancer(serviceName);

            if (loadBalancer == null)
                throw new NoSuchServiceException("There is no service to which the request is sent: %s".formatted(serviceName));

            var serviceInstance = loadBalancer.balance();

            if (serviceInstance == null)
                throw new NoSuchServiceException("There is no service to which the request is sent: %s".formatted(serviceName));

            context.setReleaseCallback(() -> loadBalancer.release(serviceInstance));

            request.setScheme("http");
            request.setHost(serviceInstance.getAddress());
            request.setPort(serviceInstance.getPort());
        }
        else {
            request.setScheme(uri.getScheme());
            request.setHost(uri.getHost());
            request.setPort(uri.getPort());
            request.setPath(uri.getPath());
        }

        return request;
    }
}
