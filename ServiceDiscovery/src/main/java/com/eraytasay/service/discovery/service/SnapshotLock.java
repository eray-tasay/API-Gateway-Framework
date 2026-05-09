package com.eraytasay.service.discovery.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class SnapshotLock {
    private final ReentrantReadWriteLock m_readWriteLock;
    private final Lock m_readLock;
    private final Lock m_writeLock;

    public SnapshotLock()
    {
        m_readWriteLock = new ReentrantReadWriteLock();
        m_readLock = m_readWriteLock.readLock();
        m_writeLock = m_readWriteLock.writeLock();
    }

    public Lock getShapshotLock()
    {
        return m_writeLock;
    }

    public Lock getWriteLock()
    {
        return m_readLock;
    }
}
