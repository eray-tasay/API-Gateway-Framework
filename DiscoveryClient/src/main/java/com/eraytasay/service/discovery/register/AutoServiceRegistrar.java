package com.eraytasay.service.discovery.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AutoServiceRegistrar implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(AutoServiceRegistrar.class);

    private final ServiceRegistrar m_serviceRegistrar;

    public AutoServiceRegistrar(ServiceRegistrar serviceRegistrar)
    {
        m_serviceRegistrar = serviceRegistrar;
    }

    @Override
    public void run(ApplicationArguments args)
    {
        register();
    }

    public void register()
    {
        try {
            m_serviceRegistrar.register();
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());

            // If microservice cannot be registered with API Gateway, there is no reason to continue
            throw ex;
        }
    }
}
