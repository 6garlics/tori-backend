package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.dto.DiaryRequest;
import com.site.bemystory.service.BookService;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;
    private final UserService userService;
    private final BookService bookService;

    /**
     * 일기 저장
     */
    @PostMapping("/diary")
    public ResponseEntity<DiaryDTO.ResponseId> saveDiary(Authentication authentication, @RequestBody DiaryDTO.Request request){
        User user = userService.findUser(authentication.getName());
        //DB 저장
        Diary diary = request.toEntity(user);
        return ResponseEntity.ok().body(diaryService.save(diary));
    }

    /**
     * 일기 조회
     */
    @GetMapping("/books/{bookId}/diary")
    public ResponseEntity<DiaryRequest> showDiary(Authentication authentication, @PathVariable Long bookId) throws Exception {
        return ResponseEntity.ok()
                .body(diaryService.findDiary(authentication, bookId)
                        .orElseThrow().toRequest());
    }

    @PostMapping("/diarytest")
    public String test(Authentication authentication, @RequestBody DiaryDTO.Request request){
        User user = userService.findUser(authentication.getName());
        log.info("userName : {}",user.getUserName());
        //DB 저장
        Diary diary = request.toEntity(user);
        diaryService.save(diary);
        return "redirect:/booktest?id="+diary.getId();
    }

}
