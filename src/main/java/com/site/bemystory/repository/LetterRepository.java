package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Letter;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.LetterResponseByBook;
import com.site.bemystory.dto.LetterResponseByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<LetterResponseByBook> findAllByBook(Book book);
    List<Letter> findAllByToUser(User user);
}
