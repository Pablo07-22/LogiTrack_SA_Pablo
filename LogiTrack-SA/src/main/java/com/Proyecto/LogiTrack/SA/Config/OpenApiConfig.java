package com.Proyecto.LogiTrack.SA.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DOCUMENTADA CAMPUS")
                        .version("1.0")
                        .description("DOCUMENTACION OFICIAL DEL SISTEMA"));
    }
}