package com.eraytasay.wafflegateway.datasource.updater;

import com.eraytasay.wafflegateway.datasource.IMutableServiceDataSource;
import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.updater.handler.IFetchAllUpdateFailureHandler;
import com.eraytasay.wafflegateway.datasource.updater.handler.IFetchAllUpdateSuccessHandler;
import com.eraytasay.wafflegateway.discovery.client.IFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.client.response.IFetchAllQueryResponseHandler;
import org.springframework.http.ResponseEntity;

public class FetchAllServiceDataSourceUpdater<T> implements IFetchAllServiceDataSourceUpdater {
    private IFetchAllServiceDiscoveryClient<T> m_client;
    private IFetchAllQueryResponseHandler<T> m_responseHandler;
    private IMutableServiceDataSource m_serviceDataSource;
    private IFetchAllUpdateSuccessHandler<T> m_successHandler;
    private IFetchAllUpdateFailureHandler m_failureHandler;

    public IFetchAllServiceDiscoveryClient<T> getClient()
    {
        return m_client;
    }

    public void setClient(IFetchAllServiceDiscoveryClient<T> client)
    {
        m_client = client;
    }

    public IFetchAllQueryResponseHandler<T> getResponseHandler()
    {
        return m_responseHandler;
    }

    public void setResponseHandler(IFetchAllQueryResponseHandler<T> responseHandler)
    {
        m_responseHandler = responseHandler;
    }

    public IMutableServiceDataSource getServiceDataSource()
    {
        return m_serviceDataSource;
    }

    public void setServiceDataSource(IMutableServiceDataSource serviceDataSource)
    {
        m_serviceDataSource = serviceDataSource;
    }

    public IFetchAllUpdateSuccessHandler<T> getSuccessHandler()
    {
        return m_successHandler;
    }

    public void setSuccessHandler(IFetchAllUpdateSuccessHandler<T> successHandler)
    {
        m_successHandler = successHandler;
    }

    public IFetchAllUpdateFailureHandler getFailureHandler()
    {
        return m_failureHandler;
    }

    public void setFailureHandler(IFetchAllUpdateFailureHandler failureHandler)
    {
        m_failureHandler = failureHandler;
    }

    @Override
    public void update()
    {
        ResponseEntity<T> response;

        try {
            response = m_client.fetchAll();
            IServiceDataSource fetchedDataSource = m_responseHandler.getDataSource(response);
            m_serviceDataSource.refresh(fetchedDataSource);
        }
        catch (Exception ex) {
            if (m_failureHandler != null) {
                m_failureHandler.onUpdateFailure(ex);
                return;
            }

            throw ex;
        }

        if (m_successHandler != null)
            m_successHandler.onUpdateSuccess(response, m_responseHandler);
    }
}
