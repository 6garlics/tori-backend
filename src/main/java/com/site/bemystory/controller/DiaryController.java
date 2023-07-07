package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.StoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiaryController {
    private final DiaryService diaryService;
    private final StoryBookService storyBookService;

    @Autowired
    public DiaryController(DiaryService diaryService, StoryBookService storyBookService) {
        this.diaryService = diaryService;
        this.storyBookService = storyBookService;
    }

    /**
     * 일기 저장하고 fastapi로 넘겨줌
     * return을 뭘로 해야할까? 일단 StoryBook으로 함
     *
     */

    @PostMapping("/books")
    public String create(@RequestBody DiaryForm diaryForm){
        //DB 저장
        Diary diary = new Diary();
        diary.setDate(diaryForm.getDate());
        diary.setSubject(diaryForm.getSubject());
        diary.setContents(diaryForm.getContents());
        diary.setStory_type(diaryForm.getStory_type());
        diaryService.save(diary);
        return "redirect:/storybook?id="+diary.getId();


    }


    @PostMapping("/test")
    public ResponseEntity<DiaryForm> corse(@RequestBody DiaryForm diaryForm){
        return ResponseEntity.ok(diaryForm);
    }

    @GetMapping("/test1")
    public ResponseEntity<String> test(){
        String st = "안녕하세요";
        return ResponseEntity.ok(st);
    }
}
