package com.pushkar.developerlifeos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI developerLifeOSApi(){

        return new OpenAPI()

                .info(

                        new Info()

                                .title("Developer-Life-OS API")

                                .version("1.0")

                                .description(
                                        "Task Management REST API built using Spring Boot"
                                )

                );
    }

}