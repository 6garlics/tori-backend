package com.site.bemystory.service;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Reply;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.ReplyDTO;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.ReplyRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    /**
     * 모댓글 달기
     */
    @Transactional
    public String writeReply(Long bId, String writer, ReplyDTO reply){
        User wuser =  userRepository.findByUserName(writer).orElseThrow();
        log.info("writer: {}", wuser.getUser_id());
        Book book = bookRepository.findById(bId).orElseThrow();
        log.info("book: {}", book.getTitle());
        Reply r = Reply.builder()
                .grp(1l)
                .grps(0l)
                .writer(wuser)
                .grpl(0)
                .book(book)
                .build();
        r.setDate(reply.getDate());
        r.setContent(reply.getContent());
        replyRepository.save(r);
        r.setGrp(r.getId());
        return "Success";
    }
}
