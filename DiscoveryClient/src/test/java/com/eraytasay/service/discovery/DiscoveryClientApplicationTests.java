package com.eraytasay.service.discovery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"service.auto-register.enabled=false"
})
class DiscoveryClientApplicationTests {
	@Test
	void contextLoads()
	{}
}
