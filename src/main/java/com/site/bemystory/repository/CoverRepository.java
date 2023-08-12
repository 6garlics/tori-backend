package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Cover;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CoverRepository {
    private final EntityManager em;

    public Cover save(Cover cover){
        em.persist(cover);
        return cover;
    }

    public Optional<Cover> findByBook(Book book){
        List<Cover> result = em.createQuery("select c from Cover c where c.isDeleted = :bool and c.book.bookId = :bookId", Cover.class)
                .setParameter("bool", false)
                .setParameter("bookId", book.getBookId())
                .getResultList();
        return result.stream().findAny();
    }
}
