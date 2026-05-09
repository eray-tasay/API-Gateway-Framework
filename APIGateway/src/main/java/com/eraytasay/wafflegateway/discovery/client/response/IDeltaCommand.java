package com.eraytasay.wafflegateway.discovery.client.response;

import com.eraytasay.wafflegateway.datasource.IMutableServiceDataSource;

@FunctionalInterface
public interface IDeltaCommand {
    void apply(IMutableServiceDataSource dataSource);
}
