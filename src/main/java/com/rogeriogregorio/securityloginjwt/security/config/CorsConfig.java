package com.rogeriogregorio.securityloginjwt.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(
                        "GET", "POST", "PATCH",
                        "PUT", "DELETE", "OPTIONS"
                )
                .allowedHeaders(
                        "Origin", "Content-Type", "X-Auth-Token",
                        "Authorization", "Cache-Control", "Content-Type",
                        "Content-Length", "Host", "User-Agent", "Accept",
                        "Accept-Encoding", "Connection"
                );
    }
}
