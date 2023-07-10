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
                .allowedMethods("")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        "OPTIONS"
                )
                .allowedHeaders("Content-Type")
                //.allowedHeaders("refresh-token")
                .allowCredentials(false)
                .maxAge(3000)
                ;
    }
}
