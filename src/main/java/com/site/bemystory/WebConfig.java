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
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("")
                .allowedOrigins("")
                .allowedMethods(HttpMethod.GET.name())
                .allowedMethods(HttpMethod.POST.name())
                .allowedMethods(HttpMethod.PUT.name())
                .allowedMethods(HttpMethod.DELETE.name())
                .allowedMethods("OPTIONS")
                .allowedHeaders("*")
                //.allowedHeaders("refresh-token")
                .allowCredentials(false)
                ;
    }
}
