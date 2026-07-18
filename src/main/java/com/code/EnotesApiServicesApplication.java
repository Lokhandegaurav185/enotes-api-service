package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnotesApiServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesApiServicesApplication.class, args);
	}

}
