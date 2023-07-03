package com.site.bemystory.repository;

import com.site.bemystory.domain.StoryBook;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JpaStoryBookRepositoryTest {
    public final EntityManager em;
    JpaStoryBookRepository storyBookRepository;

    JpaStoryBookRepositoryTest(EntityManager em) {
        this.em = em;
    }

    @BeforeEach
    void beforeEach(){
        storyBookRepository = new JpaStoryBookRepository(em);
    }

    @Test
    void 저장(){

    }

}