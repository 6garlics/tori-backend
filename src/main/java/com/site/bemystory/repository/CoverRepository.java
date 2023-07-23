package com.site.bemystory.repository;

import com.site.bemystory.domain.Cover;
import jakarta.persistence.EntityManager;

public class CoverRepository {
    private final EntityManager em;

    public CoverRepository(EntityManager em) {
        this.em = em;
    }

    public Cover save(Cover cover){
        em.persist(cover);
        return cover;
    }
}
