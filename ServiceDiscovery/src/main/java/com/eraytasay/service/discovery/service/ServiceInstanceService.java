package com.eraytasay.service.discovery.service;

import com.eraytasay.service.discovery.dto.heartbeating.HeartBeatingRequestDto;
import com.eraytasay.service.discovery.dto.serverinstance.UnregisterServiceDto;
import com.eraytasay.service.discovery.dto.serverinstance.ServerInstanceRegisterDto;
import com.eraytasay.service.discovery.dto.serverinstance.ServicesDto;
import com.eraytasay.service.discovery.repository.service.ServiceRepository;
import com.eraytasay.service.discovery.repository.service.entity.IServiceInstance;
import com.eraytasay.service.discovery.repository.update.UpdateRepository;
import com.eraytasay.service.discovery.repository.update.entity.Type;
import com.eraytasay.service.discovery.repository.update.entity.Update;
import com.eraytasay.service.discovery.dto.serverinstance.ServiceInstanceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServiceInstanceService {
    private static final Logger log = LoggerFactory.getLogger(ServiceInstanceService.class);

    private final ServiceRepository m_serviceRepository;
    private final UpdateRepository m_updateRepository;
    private final SnapshotLock m_snapshotLock;
    private final AtomicLong m_updateCounter;

    public ServiceInstanceService(ServiceRepository serviceRepository, UpdateRepository updateRepository, SnapshotLock snapshotLock,
                                  @Qualifier("updateCounter") AtomicLong updateCounter)
    {
        m_serviceRepository = serviceRepository;
        m_updateRepository = updateRepository;
        m_snapshotLock = snapshotLock;
        m_updateCounter = updateCounter;
    }

    @Value("${service-discovery.dead-services.ttl}")
    private Duration m_deadServicesTTL;

    public ServicesDto findAll()
    {
        ServicesDto servicesDto;

        m_snapshotLock.getShapshotLock().lock();

        try {
            servicesDto = new ServicesDto();

            servicesDto.setServices(m_serviceRepository.findAll());
            servicesDto.setSnapshotTimestamp(System.currentTimeMillis());
        }
        finally {
            m_snapshotLock.getShapshotLock().unlock();
        }

        return servicesDto;
    }

    public void register(String ip, ServerInstanceRegisterDto serverInstanceBody)
    {
        m_snapshotLock.getWriteLock().lock();

        try {
            var serverInstance = new ServiceInstanceDto(serverInstanceBody.getServiceName(), ip, serverInstanceBody.getPort(),
                    serverInstanceBody.getLoadBalancingAlgorithm());

            var si = m_serviceRepository.save(serverInstance);
            var update = new Update(Type.REGISTER, si);

            m_updateRepository.save(update);
            m_updateCounter.incrementAndGet();

            log.info("Service with id {} is registered.", si);
        }
        finally {
            m_snapshotLock.getWriteLock().unlock();
        }
    }

    public void processHeartBeatingRequest(String ip, HeartBeatingRequestDto heartBeatingRequestDto)
    {
        var id = getServiceId(ip, heartBeatingRequestDto.getPort());

        m_serviceRepository.updateLastHeartBeatTime(id);
        log.info("Heart beating request for service {} is processed.", id);
    }

    public void unregister(String ip, UnregisterServiceDto unregisterServiceDto)
    {
        m_snapshotLock.getWriteLock().lock();

        try {
            var id = getServiceId(ip, unregisterServiceDto.getPort());
            var serviceName = unregisterServiceDto.getServiceName();

            var removed = m_serviceRepository.deleteById(id);

            var update = new Update(Type.UNREGISTER, removed);

            m_updateRepository.save(update);
            m_updateCounter.incrementAndGet();

            log.info("Service {}-{} is unregistered successfully.", serviceName, id);
        }
        finally {
            m_snapshotLock.getWriteLock().unlock();
        }
    }

    public void deleteDeadServices()
    {
        m_snapshotLock.getWriteLock().lock();

        try {
            m_serviceRepository.deleteByCondition(si -> {
                if (isDeadService(si)) {
                    log.info("Service {}-{} is assumed dead and is deleted.", si.getServiceName(), si.getServiceId());

                    m_updateRepository.save(new Update(Type.DEAD, si));
                    m_updateCounter.incrementAndGet();

                    return true;
                }
                return false;
            });
        }
        finally {
            m_snapshotLock.getWriteLock().unlock();
        }
    }

    private boolean isDeadService(IServiceInstance si)
    {
        return System.currentTimeMillis() - si.getLastHeartBeatTime() > m_deadServicesTTL.toMillis();
    }

    private static String getServiceId(String address, int port)
    {
        return String.format("%s:%d", address, port);
    }
}
