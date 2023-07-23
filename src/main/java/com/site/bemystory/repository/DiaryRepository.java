package com.site.bemystory.repository;

import com.site.bemystory.domain.Diary;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DiaryRepository {
    private final EntityManager em;

    public DiaryRepository(EntityManager em) {
        this.em = em;
    }


    public Diary save(Diary diary) {
        em.persist(diary);
        return diary;
    }


    public Optional<Diary> findById(Long id) {
        Diary diary = em.find(Diary.class, id);
        return Optional.ofNullable(diary);
    }


    public Optional<Diary> findByTitle(String title) {
        List<Diary> result = em.createQuery("select d from Diary d where d.title = :title", Diary.class)
                .setParameter("title", title)
                .getResultList();
        return result.stream().findAny();
    }


    public List<Diary> findAll() {
        return em.createQuery("select d from Diary d", Diary.class)
                .getResultList();
    }
}
