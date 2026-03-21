package com.nexaverse.nexaverse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nexaVerseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NexaVerse API")
                        .description("AI-Powered Metaverse Platform")
                        .version("1.0.0"));
    }
}
