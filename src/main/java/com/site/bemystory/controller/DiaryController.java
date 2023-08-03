package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;


    /**
     * 일기 저장하고 fastapi로 넘겨줌
     */
    @PostMapping("/books")
    public String create(@RequestBody DiaryDTO.Request request){
        //DB 저장
        Diary diary = request.toEntity();
        diaryService.save(diary);
        return "redirect:/book?id="+diary.getId();

    }

    @PostMapping("/diarytest")
    public String test(@RequestBody DiaryDTO.Request request){
        Diary diary = request.toEntity();
        System.out.println("와우"+diary.getDate());
        diaryService.save(diary);
        return "redirect:/booktest?id="+diary.getId();
    }

}
