package com.eraytasay.wafflegateway.loadbalancer.manager;

import com.eraytasay.wafflegateway.datasource.IServiceChangeListener;
import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.IServiceLoadBalancer;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a service load balancer manager implementation. It listens to changes in a service data source and
 * reacts to them.
 */
public class ListeningServiceLoadBalancerManager implements IServiceChangeListener, IMutableServiceLoadBalancerManager {
    private static final Logger log = LoggerFactory.getLogger(ListeningServiceLoadBalancerManager.class);

    private final IMutableServiceLoadBalancerManager m_loadBalancerManager;

    private ListeningServiceLoadBalancerManager(IMutableServiceLoadBalancerManager manager)
    {
        m_loadBalancerManager = manager;
    }

    public static ListeningServiceLoadBalancerManager of()
    {
        return new ListeningServiceLoadBalancerManager(MutableServiceLoadBalancerManager.of());
    }

    public static ListeningServiceLoadBalancerManager of(IServiceDataSource dataSource)
    {
        return new ListeningServiceLoadBalancerManager(MutableServiceLoadBalancerManager.of(dataSource));
    }

    @Override
    public void onServiceAdded(ServiceInstance s)
    {
        log.info("ServiceAdded event is processed {}", s.getServiceId());
        addService(s);
    }

    @Override
    public void onServiceRemoved(ServiceInstance s)
    {
        log.info("ServiceRemoved event is processed {}", s.getServiceId());
        deleteService(s);
    }

    @Override
    public void onRefreshed(IServiceDataSource dataSource)
    {
        log.info("ServiceRefreshed event is processed. {} number of services.", dataSource.size());
        refresh(dataSource);
    }

    @Override
    public IServiceLoadBalancer getServiceLoadBalancer(String serviceName)
    {
        return m_loadBalancerManager.getServiceLoadBalancer(serviceName);
    }

    @Override
    public Iterable<String> getServiceNames()
    {
        return m_loadBalancerManager.getServiceNames();
    }

    @Override
    public void addService(ServiceInstance s)
    {
        m_loadBalancerManager.addService(s);
    }

    @Override
    public void deleteService(ServiceInstance s)
    {
        m_loadBalancerManager.deleteService(s);
    }

    @Override
    public void refresh(IServiceDataSource dataSource)
    {
        m_loadBalancerManager.refresh(dataSource);
    }
}
