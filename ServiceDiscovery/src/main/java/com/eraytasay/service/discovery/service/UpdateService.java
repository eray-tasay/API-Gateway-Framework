package com.eraytasay.service.discovery.service;

import com.eraytasay.service.discovery.dto.update.UpdatesDto;
import com.eraytasay.service.discovery.repository.service.ServiceRepository;
import com.eraytasay.service.discovery.repository.update.UpdateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UpdateService {
    private final UpdateRepository m_updateRepository;
    private final ServiceRepository m_serviceRepository;
    private final SnapshotLock m_snapshotLock;
    private final AtomicLong m_updateCounter;

    @Value("${service-discovery.old-updates.ttl}")
    private Duration m_serviceExpiryDuration;

    public UpdateService(UpdateRepository updateRepository, ServiceRepository serviceRepository,
                         SnapshotLock snapshotLock, @Qualifier("updateCounter") AtomicLong updateCounter)
    {
        m_updateRepository = updateRepository;
        m_serviceRepository = serviceRepository;
        m_snapshotLock = snapshotLock;
        m_updateCounter = updateCounter;
    }

    public UpdatesDto findAfter(long timestamp)
    {
        UpdatesDto updatesDto;

        m_snapshotLock.getShapshotLock().lock();

        try {
            updatesDto = new UpdatesDto();

            updatesDto.setUpdates(m_updateRepository.findAfter(timestamp));
            updatesDto.setSnapshotTimestamp(System.currentTimeMillis());
            updatesDto.setDeltaVersion(m_updateCounter.get());
            updatesDto.setNumberOfServices(m_serviceRepository.count());
        }
        finally {
            m_snapshotLock.getShapshotLock().unlock();
        }

        return updatesDto;
    }

    public void deleteOldUpdates()
    {
        m_snapshotLock.getWriteLock().lock();

        try {
            var timestamp = System.currentTimeMillis() - m_serviceExpiryDuration.toMillis();

            m_updateRepository.deleteBefore(timestamp);
        }
        finally {
            m_snapshotLock.getWriteLock().unlock();
        }
    }
}
