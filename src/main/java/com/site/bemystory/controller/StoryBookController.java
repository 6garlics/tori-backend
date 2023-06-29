package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.JpaStoryBookRepository;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.StoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class StoryBookController {

    private final StoryBookService storyBookService;
    private final DiaryService diaryService;

    private final JpaStoryBookRepository storyBookRepository;
    @Autowired
    public StoryBookController(StoryBookService storyBookService, DiaryService diaryService, JpaStoryBookRepository storyBookRepository) {
        this.storyBookService = storyBookService;
        this.diaryService = diaryService;
        this.storyBookRepository = storyBookRepository;
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

    //StoryBook Jpa test
    @GetMapping("/test")
    public StoryBook store(){
        StoryBook storyBook = new StoryBook();
        storyBook.setSubject("spring");
        List<String> para = new ArrayList<>();
        para.add("abc");
        para.add("def");
        List<String> urls = new ArrayList<>();
        urls.add("naver.com");
        urls.add("daum.com");
        storyBook.setParagraphs(para);
        storyBook.setDate(LocalDate.now());
        storyBook.setStory_type("framework");
        storyBook.setImage_urls(urls);
        storyBookRepository.save(storyBook);
        return storyBook;
    }
}
