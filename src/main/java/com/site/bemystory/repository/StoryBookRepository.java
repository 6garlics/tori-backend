package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.BookDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryBookRepository extends JpaRepository<Book, Long> {
    Slice<BookDTO.BookMeta> findAllByUserNotOrderByBookIdDesc(User user, Pageable page);
}
