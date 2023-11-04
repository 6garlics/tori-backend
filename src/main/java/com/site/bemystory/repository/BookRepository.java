package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Image;
import com.site.bemystory.domain.Text;
import com.site.bemystory.dto.TextDTO;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;



import java.util.*;

@AllArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public Book save(Book book) {
        em.persist(book);
        return book;
    }

    public Optional<Book> findByDiary(Long diaryId){
        List<Book> b= em.createQuery("select b from Book b where b.diary.id=:diaryId", Book.class)
                .setParameter("diaryId", diaryId)
                .getResultList();
        return b.stream().findAny();
    }

    public List<Text> findTextList(Long bookId) {
        List<Text> texts = em.createQuery("select t from Text t where t.isDeleted = :bool and t.book.bookId = :bookId", Text.class)
                .setParameter("bool", false)
                .setParameter("bookId", bookId)
                .getResultList();
        texts.sort(Comparator.comparingInt(Text::getIndex));
        return texts;
    }

    public List<Image> findImageList(Long bookId) {
        List<Image> images = em.createQuery("select i from Image i where i.isDeleted = :bool and i.book.bookId = :bookId", Image.class)
                .setParameter("bool", false)
                .setParameter("bookId", bookId)
                .getResultList();
        Collections.sort(images, Comparator.comparingInt(Image::getIndex));
        return images;
    }

    public List<String> findTexts(Long bookId) {
        List<String> textList = new ArrayList<>();
        for (Text text : findTextList(bookId)) {
            textList.add(text.getText());
        }
        return textList;
    }

    public TextDTO findText(Long bookId, int index) {
        Text text = findTextList(bookId).get(index);
        return TextDTO.builder().text(text.getText()).build();
    }


    public Optional<Book> findById(Long id) {
        Book book = em.createQuery("select b from Book b where b.isDeleted = :bool and b.id = :id", Book.class)
                .setParameter("bool", false)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(book);
    }


    public Optional<Book> findByTitle(String title) {
        List<Book> result = em.createQuery("select b from Book b where b.isDeleted = :bool and b.title = :title", Book.class)
                .setParameter("bool", false)
                .setParameter("title", title)
                .getResultList();
        return result.stream().findAny();
    }


    public List<Book> findAll(Long userId) {
        return em.createQuery("select b from Book b where b.isDeleted = :bool and b.user.id = :userId", Book.class)
                .setParameter("bool", false)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<String> findImages(Long bookId) {
        List<String> imageList = new ArrayList<>();
        for (Image image : findImageList(bookId)) {
            imageList.add(image.getImgUrl());
        }
        return imageList;
    }

    public void deleteBook(Book book){
        em.remove(book);
    }
}
