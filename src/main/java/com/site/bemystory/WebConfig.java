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
                .allowedOrigins("https://be-my-story.vercel.app")
                .allowedOrigins("")
                .allowedOrigins("")
                .allowedMethods("GET")
                .allowedMethods("POST")
                .allowedMethods("PUT")
                .allowedMethods("DELETE")
                .allowedMethods("OPTIONS")
                .allowedHeaders("Content-Type")
                //.allowedHeaders("refresh-token")
                .allowCredentials(false)
                ;
    }
}
