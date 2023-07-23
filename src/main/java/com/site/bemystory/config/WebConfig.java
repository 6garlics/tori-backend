package com.site.bemystory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://yc2bgtwjbosyjziwdos7kbbvma0cdaql.lambda-url.eu-north-1.on.aws/ ")
                .build();
    }
}
