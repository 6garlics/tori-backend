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
        StoryBook storyBook = new StoryBook();
        storyBook.setSubject("spring");
        List<String> para = new ArrayList<>();
        para.add("abc");
        para.add("def");
        List<String> urls = new ArrayList<>();
        urls.add("naver.com");
        urls.add("daum.com");
        storyBook.setParagraphs(para);
        storyBook.setDate(LocalDate.now());
        storyBook.setStory_type("framework");
        storyBook.setImage_urls(urls);
        storyBookRepository.save(storyBook);
    }

}