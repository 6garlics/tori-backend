package com.site.bemystory.service;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Letter;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.LetterRequest;
import com.site.bemystory.exception.AppException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.UserException;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.LetterRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String send(String fromUser, String toUser, LetterRequest request){
        //없는 사용자에게 보낼 경우
        if(userRepository.findByUserName(toUser).isEmpty()){
            throw new UserException(ErrorCode.USER_NOT_FOUND, "없는 사용자입니다.");
        }
        //편지 내용이 없는 경우
        if(request.getContent().isEmpty()){
            throw new AppException(ErrorCode.INVALID_REQUEST, "편지 내용이 없습니다.");
        }

        //본인에게 편지쓸 수 없음
        if(fromUser==toUser){
            throw new AppException(ErrorCode.INVALID_REQUEST, "본인에게 편지를 쓸 수 없습니다.");
        }
        User from = userRepository.findByUserName(fromUser).orElseThrow();
        User to = userRepository.findByUserName(toUser).orElseThrow();
        Book book = bookRepository.findById(request.getBookId()).orElseThrow();
        Letter letter = new Letter(from,to, request.getContent(), book);
        letterRepository.save(letter);
        return "Success";
    }
}
