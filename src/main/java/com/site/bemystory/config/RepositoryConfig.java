package com.site.bemystory.config;

import com.site.bemystory.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public RepositoryConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public DiaryRepository diaryRepository(){
        return new DiaryRepository(em);
    }

    @Bean
    public BookRepository bookRepository(){
        return new BookRepository(em);
    }

    @Bean
    public TextRepository textRepository(){
        return new TextRepository(em);
    }

    @Bean
    public ImageRepository imageRepository(){
        return new ImageRepository(em);
    }

    @Bean
    public CoverRepository coverRepository(){
        return new CoverRepository(em);
    }
}
