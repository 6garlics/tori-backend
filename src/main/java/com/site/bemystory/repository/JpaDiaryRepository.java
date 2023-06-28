package com.site.bemystory.repository;

import com.site.bemystory.domain.Diary;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaDiaryRepository implements DiaryRepository{
    private final EntityManager em;

    public JpaDiaryRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Diary save(Diary diary) {
        em.persist(diary);
        return diary;
    }

    @Override
    public Optional<Diary> findById(Long id) {
        Diary diary = em.find(Diary.class, id);
        return Optional.ofNullable(diary);
    }

    @Override
    public Optional<Diary> findBySubject(String subject) {
        List<Diary> result = em.createQuery("select d from Diary d where d.subject = :subject", Diary.class)
                .setParameter("subject", subject)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Diary> findAll() {
        return em.createQuery("select d from Diary d", Diary.class)
                .getResultList();
    }
}
