package com.site.bemystory;

import com.amazonaws.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.35.251:3000")
                .allowedOrigins("")
                .allowedOrigins("")
                .allowedMethods("GET")
                .allowedMethods("POST")
                .allowedMethods("PUT")
                .allowedMethods("DELETE")
                .allowedMethods("OPTIONS")
                //.allowedHeaders("*")
                .allowedHeaders("refresh-token")
                .allowCredentials(true)
                ;
    }
}
