package com.site.bemystory.service;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Letter;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.LetterList;
import com.site.bemystory.dto.LetterRequest;
import com.site.bemystory.dto.LetterResponseByBook;
import com.site.bemystory.dto.LetterResponseByUser;
import com.site.bemystory.exception.AppException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.UserException;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.LetterRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LetterService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LetterRepository letterRepository;

    /**
     * 편지 보내기
     */
    public String send(String fromUser, String toUser, LetterRequest request) {
        //없는 사용자에게 보낼 경우
        if (userRepository.findByUserName(toUser).isEmpty()) {
            throw new UserException(ErrorCode.USER_NOT_FOUND, "없는 사용자입니다.");
        }
        //편지 내용이 없는 경우
        if (request.getContent().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "편지 내용이 없습니다.");
        }

        //본인에게 편지쓸 수 없음
        if (fromUser == toUser) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "본인에게 편지를 쓸 수 없습니다.");
        }

        User from = userRepository.findByUserName(fromUser).orElseThrow();
        User to = userRepository.findByUserName(toUser).orElseThrow();
        Book book = bookRepository.findById(request.getBookId()).orElseThrow();
        Letter letter = new Letter(from, to, request.getContent(), book);

        //책 주인이랑 toUser가 일치하지 않음
        if (book.getUser() != to) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "책 주인과 수신인이 일치하지 않습니다.");
        }

        letterRepository.save(letter);
        return "Success";
    }

    /**
     * 편지 리스트 by 동화
     */

    public LetterList<LetterResponseByBook> listByBook(String userName, Long bookId) {
        User user = userRepository.findByUserName(userName).orElseThrow();
        Book target = bookRepository.findById(bookId).orElseThrow();
        //책 주인이 아닌 사람이 편지를 보려고 하면 예외
        if (user != target.getUser()) {
            throw new AppException(ErrorCode.INVALID_AUTHORIZATION, "동화를 볼 수 없습니다.");
        }
        List<Letter> letters = letterRepository.findAllByBook(target);
        List<LetterResponseByBook> response = new ArrayList<>();
        for (int i = 0; i < letters.size(); i++) {
            Letter l = letters.get(i);
            response.add(
                    LetterResponseByBook.builder()
                            .letterId(l.getLetterId())
                            .date(l.getDate())
                            .content(l.getContent())
                            .fromUser(l.getFromUser().toProfile())
                            .build()
            );
        }
        return new LetterList<>(response);
    }


    /**
     * 편지 리스트 by 유저
     */
    public LetterList<LetterResponseByUser> listByUser(String userName) {
        User target = userRepository.findByUserName(userName).orElseThrow();
        List<Letter> letters = letterRepository.findAllByToUser(target);
        List<LetterResponseByUser> response = new ArrayList<>();
        for (int i = 0; i < letters.size(); i++) {
            Letter l = letters.get(i);
            Book b = l.getBook();
            response.add(
                    LetterResponseByUser.builder()
                            .letterId(l.getLetterId())
                            .date(l.getDate())
                            .content(l.getContent())
                            .book(b.toDTO(b.getCover().getUrl()))
                            .fromUser(l.getFromUser().toProfile())
                            .build()
            );

        }
        return new LetterList<>(response);
    }


}
