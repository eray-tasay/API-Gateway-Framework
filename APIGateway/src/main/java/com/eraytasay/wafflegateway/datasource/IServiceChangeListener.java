package com.eraytasay.wafflegateway.datasource;

import com.eraytasay.wafflegateway.serviceistance.ServiceInstance;

public interface IServiceChangeListener {
    void onServiceAdded(ServiceInstance s);
    void onServiceRemoved(ServiceInstance s);
    void onRefreshed(IServiceDataSource dataSource);
}