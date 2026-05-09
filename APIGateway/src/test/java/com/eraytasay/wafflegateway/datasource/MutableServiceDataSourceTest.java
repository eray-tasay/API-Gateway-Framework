package com.eraytasay.wafflegateway.datasource;

/*
public class MutableServiceDataSourceTest {
    private MutableServiceDataSource m_serviceDataSource;

    @BeforeEach
    void setUp()
    {
        m_serviceDataSource = new MutableServiceDataSource();
    }

    @Test
    void save_shouldAddNewServiceInstance()
    {
        var instance = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance);

        var siOpt = m_serviceDataSource.getService("test-service", "1.1.1.1:80" );

        assertFalse(siOpt.isEmpty());
    }

    @Test
    void save_shouldAddNewServiceInstancesWithSameName()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 85, LoadBalancing.ROUND_ROBIN);
        var instance3 = new ServiceInstance("test-service", "1.1.1.2", 80, LoadBalancing.ROUND_ROBIN);

        assertDoesNotThrow(() -> {
            m_serviceDataSource.add(instance1);
            m_serviceDataSource.add(instance2);
            m_serviceDataSource.add(instance3);
        });

        assertEquals(3, m_serviceDataSource.getServices("test-service").size());
    }

    @Test
    void save_shouldThrowException_whenServiceAlreadyExists()
    {
        var instance1 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);
        var instance2 = new ServiceInstance("test-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance1);

        assertThrows(ServiceAlreadyExistsException.class, () -> m_serviceDataSource.add(instance2));
    }

    @Test
    void remove_shouldRemoveServiceInstance()
    {
        var instance1 = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance1);

        assertDoesNotThrow(() -> m_serviceDataSource.remove(instance1));

        var siOpt = m_serviceDataSource.getService("payment-service", "127.0.0.1:8080");

        assertTrue(siOpt.isEmpty());
    }

    @Test
    void remove_shouldThrowExceptionWhenServiceNameDoesNotExist()
    {
        var ex = assertThrows(NoSuchServiceException.class, () ->
                m_serviceDataSource.remove(new ServiceInstance("unknown-service", "1.1.1.1", 80, LoadBalancing.ROUND_ROBIN)));

        assertEquals(
                "Service with name unknown-service does not exist.",
                ex.getMessage()
        );
    }

    @Test
    void remove_ShouldThrowExceptionWhenServiceIdNotExist()
    {
        var instance = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance);

        var ex = assertThrows(NoSuchServiceException.class, () ->
                        m_serviceDataSource.remove(new ServiceInstance("payment-service", "127.1.1.1", 8080, LoadBalancing.ROUND_ROBIN)));

        assertEquals(
                "Service with id 127.1.1.1:8080 does not exist.",
                ex.getMessage()
        );
    }

    @Test
    void delete_shouldRemoveServiceListWhenLastInstanceDeleted()
    {
        var instance = new ServiceInstance("payment-service", "127.0.0.1", 8080, LoadBalancing.ROUND_ROBIN);

        m_serviceDataSource.add(instance);
        m_serviceDataSource.remove(instance);

        assertEquals(0, m_serviceDataSource.getServices("payment-service").size());
    }
}
*/