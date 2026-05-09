package com.eraytasay.wafflegateway.datasource.updater.handler;

import com.eraytasay.wafflegateway.exception.TryCountExpiredException;

public class TryAgainFetchAllUpdateFailureHandler implements IFetchAllUpdateFailureHandler {
    private final int m_initialTryCount;
    private final IFetchAllUpdateFailureHandler m_failureHandler;

    private int m_tryCount;

    public TryAgainFetchAllUpdateFailureHandler(IFetchAllUpdateFailureHandler failureHandler, int tryCount)
    {
        m_initialTryCount = tryCount;
        m_tryCount = tryCount;
        m_failureHandler = failureHandler;
    }

    public int getTryCount()
    {
        return m_tryCount;
    }

    public int getInitialTryCount()
    {
        return m_initialTryCount;
    }

    public void restartTryCount()
    {
        m_tryCount = m_initialTryCount;
    }

    @Override
    public void onUpdateFailure(Exception ex)
    {
        if (m_tryCount <= 0)
            throw new TryCountExpiredException("Number of try count has expired", ex);

        m_failureHandler.onUpdateFailure(ex);
        m_tryCount--;
    }
}
