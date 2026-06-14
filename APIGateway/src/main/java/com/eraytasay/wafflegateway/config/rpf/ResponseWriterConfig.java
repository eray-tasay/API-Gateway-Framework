package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.response.ResponseWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ResponseWriterConfig {
    @Bean
    public ResponseWriter responseWriter()
    {
        return new ResponseWriter();
    }
}
