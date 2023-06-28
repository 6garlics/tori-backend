package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.StoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class StoryBookController {

    private final StoryBookService storyBookService;
    private final DiaryService diaryService;
    @Autowired
    public StoryBookController(StoryBookService storyBookService, DiaryService diaryService) {
        this.storyBookService = storyBookService;
        this.diaryService = diaryService;
    }

    @ResponseBody
    @GetMapping ("/diary-to-story")
    public StoryBook writeStory(@RequestParam("id") Long id){
        Diary diary = diaryService.findOne(id).get();
        StoryBook storyBook = storyBookService.passToAI(diary);
        storyBook.setStory_type(diary.getStory_type());
        // Todo: chat GPT가 제목도 넘겨주면 이 코드 없애도 됨
        storyBook.setSubject(diary.getSubject());
        storyBook.setDate(diary.getDate());
        Long sbId = storyBookService.saveBook(storyBook);
        return storyBookService.findOne(sbId).get();
    }
}
