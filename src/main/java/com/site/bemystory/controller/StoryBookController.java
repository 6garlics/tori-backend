package com.site.bemystory.controller;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.Page;
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

    /**
     * 동화 생성 - fastapi
     */
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
    @ResponseBody
    @GetMapping("/test")
    public StoryBook store(){
        StoryBook storyBook = new StoryBook();
        storyBook.setSubject("spring");
        List<Page> pages = new ArrayList<>();
        Page p0=new Page();
        p0.setPageId(123L);
        p0.setNumber(0);
        p0.setText("그 아이는 행복했답니다.");
        p0.setImg_url("naver.com");
        pages.add(p0);
        storyBook.setPages(pages);
        storyBook.setDate(LocalDate.now());
        storyBook.setStory_type("framework");

        storyBookRepository.save(storyBook);
        return storyBook;
    }
}
