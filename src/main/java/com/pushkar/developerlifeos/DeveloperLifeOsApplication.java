package com.pushkar.developerlifeos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeveloperLifeOsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeveloperLifeOsApplication.class, args);
	}

}
