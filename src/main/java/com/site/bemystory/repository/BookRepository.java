package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Text;
import jakarta.persistence.EntityManager;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final EntityManager em;

    public BookRepository(EntityManager em) {
        this.em = em;
    }


    public Book save(Book book) {
        em.persist(book);
        return book;
    }

    public List<Text> findTexts(Book book){
        Long bookId = book.getBookId();
        List<Text> texts = em.createQuery("select t from Text t where t.book.bookId = :bookId", Text.class)
                .setParameter("bookId", bookId)
                .getResultList();
        Collections.sort(texts, (t1, t2)->t1.getIndex() - t2.getIndex());
        return texts;
    }


    public Optional<Book> findById(Long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }


    public Optional<Book> findByTitle(String title) {
        List<Book> result = em.createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title)
                .getResultList();
        return result.stream().findAny();
    }


    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }


}
