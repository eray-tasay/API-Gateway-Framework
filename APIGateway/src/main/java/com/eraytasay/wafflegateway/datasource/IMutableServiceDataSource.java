package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.exception.NoSuchServiceException;
import com.eraytasay.wafflegateway.exception.ServiceAlreadyExistsException;
import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

/**
 * Extends {@code IServiceDataSource} by adding support for update operations on stored services.
 */
public interface IMutableServiceDataSource extends IServiceDataSource {
    /**
     * Adds the given ServiceInstance to the data source.
     * <p>
     * This method does not throw an exception when two services with the same id but different name are added.
     * This is not checked for a performance optimization.
     *
     * @param serviceInstance the serviceInstance to be added to the data source.
     * @throws ServiceAlreadyExistsException if {@code serviceInstance} has the same service name and service id with
     * another service in the data source.
     */
    void add(ServiceInstance serviceInstance);

    /**
     * Removes the given ServiceInstance. It makes ID comparison to find the service instance that will be deleted.
     *
     * @throws NoSuchServiceException if there is no such service with the given name and id.
     */
    void delete(ServiceInstance serviceInstance);

    /**
     * Replaces all current service data with the given {@code dataSource}.
     * <p>
     * Use this method only for a fresh start or to reset the entire system.
     *
     * @param dataSource the data source whose services will be used to fill in the data source.
     */
    void refresh(IServiceDataSource dataSource);
}
