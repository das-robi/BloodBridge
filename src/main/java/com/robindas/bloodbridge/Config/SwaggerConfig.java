package com.robindas.bloodbridge.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("BloodBridge-Local Emergency Blood Donation Platform")
                        .version("1.0")
                        .description("Spring Boot Restful API Documentation for BloodBridge")
                        .contact(new Contact()
                                .name("Robin Das")
                                .email("contact.robindas@gmail.com")))

                //Add Security Requirement JWT Token
                .addSecurityItem(new SecurityRequirement().addList("Bearer Auth"))

                .components(new Components()
                        .addSecuritySchemes("Bearer Auth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

}
