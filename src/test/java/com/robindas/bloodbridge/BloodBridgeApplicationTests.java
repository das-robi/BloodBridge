package com.robindas.bloodbridge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "security.jwt.secret=VGhpc0lzQVRlc3RTZWNyZXRLZXlUaGF0SXNBdExlYXN0VGhpcnR5VHdvQnl0ZXM=")
class BloodBridgeApplicationTests {

	@Test
	void contextLoads() {
	}

}
