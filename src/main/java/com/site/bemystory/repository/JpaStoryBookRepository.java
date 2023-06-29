package com.site.bemystory.repository;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.StoryBook;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaStoryBookRepository implements StoryBookRepository{

    private final EntityManager em;

    public JpaStoryBookRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public StoryBook save(StoryBook storyBook) {
        em.persist(storyBook);
        return storyBook;
    }

    @Override
    public Optional<StoryBook> findById(Long id) {
        StoryBook storyBook = em.find(StoryBook.class, id);
        return Optional.ofNullable(storyBook);
    }

    @Override
    public Optional<StoryBook> findBySubject(String subject) {
        List<StoryBook> result = em.createQuery("select s from StoryBook s where s.subject = :subject", StoryBook.class)
                .setParameter("subject", subject)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<StoryBook> findAll() {
        return em.createQuery("select s from StoryBook s", StoryBook.class)
                .getResultList();
    }
}
