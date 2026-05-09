package com.eraytasay.wafflegateway.datasource.updater.handler;

@FunctionalInterface
public interface IDeltaUpdateFailureHandler {
    void onUpdateFailure(Exception ex);
}
