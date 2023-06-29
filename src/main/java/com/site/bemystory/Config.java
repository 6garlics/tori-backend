package com.site.bemystory;

import com.site.bemystory.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;

@Configuration
public class Config {
    private final DataSource dataSource;
    private final EntityManager em;

    public Config(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public JpaDiaryRepository diaryRepository(){
        return new JpaDiaryRepository(em);
    }

    @Bean
    public JpaStoryBookRepository storyRepository(){
        return new JpaStoryBookRepository(em);
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://nkk4y3gpizy3q2h25fiae7e4ma0fezqg.lambda-url.eu-north-1.on.aws")
                .build();
    }
}
