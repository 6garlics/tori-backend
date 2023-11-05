package com.site.bemystory.repository;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Reply;
import com.site.bemystory.dto.ReplyListRequest;
import com.site.bemystory.dto.ReplyOne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBook(Book book);
}
