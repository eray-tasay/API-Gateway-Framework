package com.eraytasay.service.discovery.repository.update;

import com.eraytasay.service.discovery.repository.update.entity.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class UpdateRepository {
    private final ArrayDeque<Update> m_updates;
    private final Lock m_readLock;
    private final Lock m_writeLock;

    public UpdateRepository()
    {
        m_updates = new ArrayDeque<>();

        var readWriteLock = new ReentrantReadWriteLock();

        m_readLock = readWriteLock.readLock();
        m_writeLock = readWriteLock.writeLock();
    }

    public void save(Update update)
    {
        m_writeLock.lock();

        try {
            var newUpdate = new Update(update.getType(), update.getServiceInstance());

            m_updates.addLast(newUpdate);
        }
        finally {
            m_writeLock.unlock();
        }
    }

    public void deleteBefore(long timestamp)
    {
        m_writeLock.lock();

        try {
            var iter = m_updates.iterator();

            while (iter.hasNext()) {
                var update = iter.next();

                if (update.getOccurredAt() < timestamp)
                    iter.remove();
                else
                    break;
            }
        }
        finally {
            m_writeLock.unlock();
        }
    }

    public Iterable<Update> findAfter(long timestamp)
    {
        var res = new ArrayList<Update>();

        m_readLock.lock();

        try {
            var iter = m_updates.descendingIterator();

            while (iter.hasNext()) {
                var update = iter.next();

                if (update.getOccurredAt() > timestamp)
                    res.add(update);
                else
                    break;
            }
        }
        finally {
            m_readLock.unlock();
        }

        return res;
    }
}
