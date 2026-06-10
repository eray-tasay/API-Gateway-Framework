package com.eraytasay.wafflegateway.config.rpf;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayPropertiesConfig {
}
