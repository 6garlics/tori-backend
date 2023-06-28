package com.site.bemystory;

import com.site.bemystory.repository.MemoryDiaryRepository;
import com.site.bemystory.repository.MemoryStoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {
    @Bean
    public MemoryDiaryRepository memoryDiaryRepository(){
        return new MemoryDiaryRepository();
    }

    @Bean
    public MemoryStoryRepository memoryStoryRepository(){
        return new MemoryStoryRepository();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://nkk4y3gpizy3q2h25fiae7e4ma0fezqg.lambda-url.eu-north-1.on.aws")
                .build();
    }
}
