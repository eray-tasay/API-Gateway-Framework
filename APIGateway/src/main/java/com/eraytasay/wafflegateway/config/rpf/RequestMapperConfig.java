package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.request.RequestMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestMapperConfig {
    @Bean
    public RequestMapper requestMapper()
    {
        return new RequestMapper();
    }
}
