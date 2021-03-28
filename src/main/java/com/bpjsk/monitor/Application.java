package com.bpjsk.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application  implements WebMvcConfigurer{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Origin", "Access-Control-Request-Method", "X-Requested-With", "Content-Type" )
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
    }
}
