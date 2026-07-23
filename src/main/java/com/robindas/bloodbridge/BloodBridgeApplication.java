package com.robindas.bloodbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BloodBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloodBridgeApplication.class, args);

		System.out.println(new BCryptPasswordEncoder(12).encode("ADMIN@123"));

	}



}
