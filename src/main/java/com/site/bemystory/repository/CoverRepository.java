package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Cover;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CoverRepository {
    private final EntityManager em;

    public CoverRepository(EntityManager em) {
        this.em = em;
    }

    public Cover save(Cover cover){
        em.persist(cover);
        return cover;
    }

    public Optional<Cover> findByBook(Book book){
        List<Cover> result = em.createQuery("select c from Cover c where c.book.bookId = :bookId", Cover.class)
                .setParameter("bookId", book.getBookId())
                .getResultList();
        return result.stream().findAny();
    }
}
