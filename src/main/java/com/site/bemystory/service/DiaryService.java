package com.site.bemystory.service;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Diary;
import com.site.bemystory.exception.AuthorizationException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.DiaryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
@AllArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final BookRepository bookRepository;


    /**
     * 일기 저장
     */
    public Long save(Diary diary){
        diaryRepository.save(diary);
        return diary.getId();
    }

    /**
     * 일기 내부 조회 - 1개
     */
    public Optional<Diary> findOne(Long diaryId){
        return diaryRepository.findById(diaryId);
    }

    /**
     * 일기 외부 조회
     */
    public Optional<Diary> findDiary(Authentication authentication, Long bookId) throws AuthorizationException {
        String bOwner = bookRepository.findById(bookId).orElseThrow().getUser().getUserName();
        //만약 요청시 보낸 토큰과 {bookId}의 주인이 다르면 권한 없다는 에러 띄우기

        if(!authentication.getName().equals(bOwner)){
            throw new AuthorizationException(ErrorCode.INVALID_AUTHORIZATION, "일기를 열람할 수 있는 권한이 없습니다.");
        }
        Book book = bookRepository.findById(bookId).orElseThrow();
        return diaryRepository.findById(book.getDiary().getId());
    }

    /**
     * 일기 조회 - 모두
     */
    public List<Diary> findDiaries(){
        return diaryRepository.findAll();
    }

}
