package com.dvtsoftware.stocktrade.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(AppSecurityProperties securityProperties) {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Stock Trades API")
                        .version("1.0.0")
                        .description("REST API for creating, querying, and analysing stock trade transactions.")
                        .contact(new Contact()
                                .name("Alvin Vurayai")
                                .email("vinievie@gmail.com")));

        if (securityProperties.isEnabled()) {
            openAPI.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                    .components(new Components()
                            .addSecuritySchemes("basicAuth", new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("basic")));
        }

        return openAPI;
    }

}
