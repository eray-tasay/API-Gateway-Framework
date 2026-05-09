package com.eraytasay.service.discovery.repository;

import com.eraytasay.service.discovery.exception.NoSuchServiceException;
import com.eraytasay.service.discovery.exception.ServiceAlreadyExistsException;
import com.eraytasay.service.discovery.repository.service.ServiceRepository;
import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;
import com.eraytasay.service.discovery.repository.service.entity.LoadBalancing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceRepositoryTest {
    private ServiceRepository m_repository;

    @BeforeEach
    void setUp()
    {
        m_repository = new ServiceRepository();
    }

    @Test
    void save_shouldAddNewServiceInstance()
    {
        var instance = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_repository.save(instance);

        var siOpt = m_repository.findByServiceId("1.1.1.1:80" );

        assertFalse(siOpt.isEmpty());
    }

    @Test
    void save_shouldAddNewServiceInstancesWithSameName()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 85, LoadBalancing.ROUND_ROBIN);
        var instance3 = new ServiceInstance("test-service", "1.1.1.2", 80, LoadBalancing.ROUND_ROBIN);

        assertDoesNotThrow(() -> {
            m_repository.save(instance1);
            m_repository.save(instance2);
            m_repository.save(instance3);
        });

        assertTrue(m_repository.findByServiceId(instance1.getServiceId()).isPresent());
        assertTrue(m_repository.findByServiceId(instance2.getServiceId()).isPresent());
        assertTrue(m_repository.findByServiceId(instance3.getServiceId()).isPresent());
    }

    @Test
    void save_shouldThrowException_whenServiceAlreadyExists()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance3 = new ServiceInstance("order-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_repository.save(instance1);

        assertThrows(ServiceAlreadyExistsException.class, () -> m_repository.save(instance2));
        assertThrows(ServiceAlreadyExistsException.class, () -> m_repository.save(instance3));
    }

    @Test
    void deleteById_shouldDeleteServiceInstance()
    {
        var instance1 = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_repository.save(instance1);

        assertDoesNotThrow(() -> m_repository.deleteById(instance1.getServiceId()));

        var siOpt = m_repository.findByServiceId(instance1.getServiceId());

        assertTrue(siOpt.isEmpty());
    }

    @Test
    void deleteById_shouldThrowExceptionWhenServiceIdDoesNotExist()
    {
        var instance = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_repository.save(instance);

        var ex = assertThrows(NoSuchServiceException.class, () ->
                        m_repository.deleteById("id-999"));

        assertEquals(
                "Service with id id-999 does not exist.",
                ex.getMessage()
        );
    }

    @Test
    void deleteById_shouldDeleteServiceListWhenLastInstanceDeleted()
    {
        var instance = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_repository.save(instance);
        m_repository.deleteById(instance.getServiceId());

        assertEquals(0, count(m_repository.findAll()));
    }

    private int count(Iterable<IServiceInstance> services)
    {
        var res = 0;

        for (var s : services)
            res++;

        return res;
    }
}
