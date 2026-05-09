package com.eraytasay.wafflegateway;

import com.eraytasay.wafflegateway.config.prop.GatewayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GatewayProperties.class)
public class WaffleGatewayApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(WaffleGatewayApplication.class, args);
	}
}
