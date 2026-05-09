package com.eraytasay.wafflegateway.datasource.updater;

import com.eraytasay.wafflegateway.datasource.IMutableServiceDataSource;
import com.eraytasay.wafflegateway.datasource.updater.handler.IDeltaUpdateFailureHandler;
import com.eraytasay.wafflegateway.datasource.updater.handler.IDeltaUpdateSuccessHandler;
import com.eraytasay.wafflegateway.datasource.validator.IDeltaUpdateValidator;
import com.eraytasay.wafflegateway.discovery.client.IDeltaServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import org.springframework.http.ResponseEntity;

public class DeltaServiceDataSourceUpdater<T> implements IDeltaServiceDataSourceUpdater {
    private IDeltaServiceDiscoveryClient<T> m_client;
    private IDeltaQueryResponseHandler<T> m_responseHandler;
    private IMutableServiceDataSource m_serviceDataSource;
    private IDeltaUpdateFailureHandler m_failureHandler;
    private IDeltaUpdateSuccessHandler<T> m_successHandler;
    private IDeltaUpdateValidator<T> m_deltaUpdateValidator;

    public IDeltaServiceDiscoveryClient<T> getClient()
    {
        return m_client;
    }

    public void setClient(IDeltaServiceDiscoveryClient<T> client)
    {
        m_client = client;
    }

    public IDeltaQueryResponseHandler<T> getResponseHandler()
    {
        return m_responseHandler;
    }

    public void setResponseHandler(IDeltaQueryResponseHandler<T> responseHandler)
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

    public IDeltaUpdateFailureHandler getFailureHandler()
    {
        return m_failureHandler;
    }

    public void setFailureHandler(IDeltaUpdateFailureHandler failureHandler)
    {
        m_failureHandler = failureHandler;
    }

    public IDeltaUpdateSuccessHandler<T> getSuccessHandler()
    {
        return m_successHandler;
    }

    public void setSuccessHandler(IDeltaUpdateSuccessHandler<T> successHandler)
    {
        m_successHandler = successHandler;
    }

    public IDeltaUpdateValidator<T> getDeltaUpdateValidator()
    {
        return m_deltaUpdateValidator;
    }

    public void setDeltaUpdateValidator(IDeltaUpdateValidator<T> deltaUpdateValidator)
    {
        m_deltaUpdateValidator = deltaUpdateValidator;
    }

    @Override
    public void update(long occurredAfter)
    {
        ResponseEntity<T> response;

        try {
            response = m_client.fetchUpdates(occurredAfter);
            var commands = m_responseHandler.getCommands(response);

            commands.forEach(cmd -> cmd.apply(m_serviceDataSource));

            if (m_deltaUpdateValidator != null)
                m_deltaUpdateValidator.validate(response, m_responseHandler);
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
