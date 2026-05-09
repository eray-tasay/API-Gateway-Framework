package com.eraytasay.wafflegateway.datasource.updater.handler;

@FunctionalInterface
public interface IFetchAllUpdateFailureHandler {
    void onUpdateFailure(Exception ex);
}
