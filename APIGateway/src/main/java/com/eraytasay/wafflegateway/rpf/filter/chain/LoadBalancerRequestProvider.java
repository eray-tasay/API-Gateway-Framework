package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.loadbalancer.manager.IServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.request.Request;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

import java.net.URI;

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

            updateHostHeader(request, serviceInstance);
        }
        else
            updateHostHeader(request, uri);

        return request;
    }

    private static void updateHostHeader(Request request, ServiceInstance serviceInstance)
    {
        var ip = serviceInstance.getAddress();
        var port = serviceInstance.getPort();

        // Mutate the request so that it is sent to the resolved service instance.
        request.getHeaders()
                .mutate("Host")
                .clear()
                .addLast("%s:%d".formatted(ip, port));
    }

    private static void updateHostHeader(Request request, URI uri)
    {
        request.getHeaders()
                .mutate("Host")
                .clear()
                .addLast(uri.getHost());
    }
}
