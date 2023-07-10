package com.site.bemystory.controller;

import com.site.bemystory.domain.*;
import com.site.bemystory.repository.JpaStoryBookRepository;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.StoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping ("/storybook")
    public ResponseEntity writeStory(@RequestParam("id") Long id){
        //diary id로 동화로 바꾸려는 일기 조회
        Diary diary = diaryService.findOne(id).get();
        System.out.println(diary.getId());
        //fastapi로 보내서 BookForm 받아옴
        BookForm bookForm = storyBookService.passToAI(diary);
        System.out.println(bookForm.getParagraphs());
        System.out.println(bookForm.getImg_urls());
        //동화책 만들기
        StoryBook storyBook = new StoryBook();
        storyBook.setStory_type(bookForm.getStory_type());
        storyBook.setSubject(bookForm.getSubject());
        storyBook.setDate(bookForm.getDate());
        storyBookService.makePages(bookForm, storyBook);
        storyBookService.saveBook(storyBook);
        return ResponseEntity.ok().build();
    }



    @ResponseBody
    @GetMapping("/pagetest")
    public ResponseEntity<Book> page(){
        StoryBook storyBook = storyBookService.findOne(1L).get();
        Book book = new Book();
        book.setStoryBook(storyBook);
        book.setPages(storyBookService.findPage(storyBook));
        return ResponseEntity.ok(book);
    }

}