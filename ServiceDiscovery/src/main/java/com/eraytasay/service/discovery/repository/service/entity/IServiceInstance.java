package com.eraytasay.service.discovery.repository.service.entity;

public interface IServiceInstance {
    String getAddress();
    int getPort();
    String getServiceName();
    String getServiceId();
    LoadBalancing getLoadBalancingAlgorithm();
    long getLastHeartBeatTime();
}
