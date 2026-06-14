package com.eraytasay.wafflegateway.unit;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableLeastConnectionLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.algorithm.MutableRoundRobinLoadBalancer;
import com.eraytasay.wafflegateway.loadbalancer.manager.MutableServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.serviceistance.LoadBalancing;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MutableServiceLoadBalancerManagerTest {
    @Test
    public void check_count_after_refresh()
    {
        var manager = createManagerWithActiveConnections();
        var refreshedDataSource = createMockedDataSource();

        manager.refresh(refreshedDataSource);

        assertEquals(3, manager.getServiceLoadBalancer("order-service").getSize());
        assertEquals(1, manager.getServiceLoadBalancer("user-service").getSize());
        assertEquals(1, manager.getServiceLoadBalancer("payment-service").getSize());
    }

    @Test
    public void check_load_balancer_types()
    {
        var manager = createManagerWithActiveConnections();
        var refreshedDataSource = createMockedDataSource();

        manager.refresh(refreshedDataSource);

        assertInstanceOf(MutableLeastConnectionLoadBalancer.class, manager.getServiceLoadBalancer("order-service"));
        assertInstanceOf(MutableRoundRobinLoadBalancer.class, manager.getServiceLoadBalancer("user-service"));
        assertInstanceOf(MutableLeastConnectionLoadBalancer.class, manager.getServiceLoadBalancer("order-service"));
    }

    @Test
    public void should_preserve_old_counts()
    {
        var manager = createManagerWithActiveConnections();
        var refreshedDataSource = createMockedDataSource();

        manager.refresh(refreshedDataSource);

        var loadsByIdMap = getLoadsByIdMap((MutableLeastConnectionLoadBalancer)manager.getServiceLoadBalancer("order-service"));

        assertEquals(20, loadsByIdMap.get("193.20.13.13:8080"));
        assertEquals(40, loadsByIdMap.get("192.23.23.14:8080"));
    }

    @Test
    public void should_load_values_start_from_zero()
    {
        var manager = createManagerWithActiveConnections();
        var refreshedDataSource = createMockedDataSource();

        manager.refresh(refreshedDataSource);

        var loadsByIdMap = getLoadsByIdMap((MutableLeastConnectionLoadBalancer)manager.getServiceLoadBalancer("order-service"));

        assertEquals(0, loadsByIdMap.get("192.23.23.100:8080"));

        loadsByIdMap = getLoadsByIdMap((MutableLeastConnectionLoadBalancer)manager.getServiceLoadBalancer("payment-service"));

        assertEquals(0, loadsByIdMap.get("193.20.13.17:8080"));
    }

    private Map<String, Integer> getLoadsByIdMap(MutableLeastConnectionLoadBalancer balancer)
    {
        try {
            var field = MutableLeastConnectionLoadBalancer.class.getDeclaredField("m_loadsById");
            field.setAccessible(true);

            return (Map<String, Integer>)field.get(balancer);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private MutableServiceLoadBalancerManager createManagerWithActiveConnections()
    {
        var res = MutableServiceLoadBalancerManager.of();

        var s1 = new ServiceInstance("order-service", "192.23.23.12", 8080, LoadBalancing.LEAST_CONNECTION);
        var s2 = new ServiceInstance("order-service", "193.20.13.13", 8080, LoadBalancing.LEAST_CONNECTION);
        var s3 = new ServiceInstance("order-service", "192.23.23.14", 8080, LoadBalancing.LEAST_CONNECTION);
        var s4 = new ServiceInstance("user-service", "193.20.13.15", 8080, LoadBalancing.LEAST_CONNECTION);
        var s5 = new ServiceInstance("user-service", "192.23.23.16", 8080, LoadBalancing.LEAST_CONNECTION);
        var s6 = new ServiceInstance("user-service", "193.20.13.17", 8080, LoadBalancing.LEAST_CONNECTION);

        res.addService(s1);

        var balancer = (MutableLeastConnectionLoadBalancer)res.getServiceLoadBalancer(s1.getServiceName());

        balancer.addServiceWithLoad(s2, 20);
        balancer.addServiceWithLoad(s3, 40);

        res.addService(s4);

        balancer = (MutableLeastConnectionLoadBalancer)res.getServiceLoadBalancer(s4.getServiceName());

        balancer.addServiceWithLoad(s5, 2);
        balancer.addServiceWithLoad(s6, 3);

        return res;
    }

    private IServiceDataSource createMockedDataSource()
    {
        var s1 = new ServiceInstance("order-service", "192.23.23.100", 8080, LoadBalancing.LEAST_CONNECTION);
        var s2 = new ServiceInstance("order-service", "193.20.13.13", 8080, LoadBalancing.LEAST_CONNECTION);
        var s3 = new ServiceInstance("order-service", "192.23.23.14", 8080, LoadBalancing.LEAST_CONNECTION);
        var s4 = new ServiceInstance("user-service", "193.20.13.15", 8080, LoadBalancing.ROUND_ROBIN);
        var s5 = new ServiceInstance("payment-service", "193.20.13.17", 8080, LoadBalancing.LEAST_CONNECTION);

        var mockedDataSource = mock(IServiceDataSource.class);

        when(mockedDataSource.getServices()).thenReturn(List.of(s1, s2, s3, s4, s5));

        return mockedDataSource;
    }
}
