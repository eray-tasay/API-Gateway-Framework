package com.eraytasay.service.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiscoveryClientApplication {
	public static void main(String[] args)
    {
		SpringApplication.run(DiscoveryClientApplication.class, args);
	}
}
