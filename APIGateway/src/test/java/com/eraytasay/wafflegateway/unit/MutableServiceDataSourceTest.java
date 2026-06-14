package com.eraytasay.wafflegateway.unit;

import com.eraytasay.wafflegateway.datasource.MutableServiceDataSource;
import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.exception.ServiceAlreadyExistsException;
import com.eraytasay.wafflegateway.serviceistance.LoadBalancing;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class MutableServiceDataSourceTest {
    private MutableServiceDataSource m_serviceDataSource;

    @BeforeEach
    void setUp()
    {
        m_serviceDataSource = MutableServiceDataSource.of();
    }

    @Test
    void add_shouldAddNewServiceInstance()
    {
        var instance = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance);

        var siOpt = m_serviceDataSource.getService(instance.getServiceId());

        assertFalse(siOpt.isEmpty());
    }

    @Test
    void add_shouldAddNewServiceInstancesWithSameName()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 85, LoadBalancing.ROUND_ROBIN);
        var instance3 = new ServiceInstance("test-service", "1.1.1.2", 80, LoadBalancing.ROUND_ROBIN);

        assertDoesNotThrow(() -> {
            m_serviceDataSource.add(instance1);
            m_serviceDataSource.add(instance2);
            m_serviceDataSource.add(instance3);
        });

        assertEquals(3, toList(m_serviceDataSource.getServices()).size());
    }

    @Test
    void add_shouldThrowException_whenServiceAlreadyExists()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance1);

        assertThrows(ServiceAlreadyExistsException.class, () -> m_serviceDataSource.add(instance2));
    }

    @Test
    void delete_shouldDeleteServiceInstance()
    {
        var instance1 = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance1);

        assertDoesNotThrow(() -> m_serviceDataSource.delete(instance1));

        var siOpt = m_serviceDataSource.getService(instance1.getServiceId());

        assertTrue(siOpt.isEmpty());
    }

    @Test
    void delete_ShouldThrowExceptionWhenServiceIdNotExist()
    {
        var instance = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance);

        var ex = assertThrows(NoSuchServiceException.class, () ->
                        m_serviceDataSource.delete(new ServiceInstance("payment-service", "127.1.1.1", 8080, LoadBalancing.ROUND_ROBIN)));

        assertEquals(
                "Service with id 127.1.1.1:8080 does not exist.",
                ex.getMessage()
        );
    }

    private static <T> List<T> toList(Iterable<T> iterable)
    {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}
