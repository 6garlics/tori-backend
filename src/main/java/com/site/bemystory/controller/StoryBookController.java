package com.site.bemystory.controller;

import com.site.bemystory.domain.BookForm;
import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.Page;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.JpaStoryBookRepository;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.StoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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
    public ResponseEntity<StoryBook> writeStory(@RequestParam("id") Long id){
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
        Long sbId = storyBookService.saveBook(storyBook);
        storyBookService.makePages(bookForm, storyBook);
        return ResponseEntity.ok(storyBookService.findOne(sbId).get());
    }

    @ResponseBody
    @PostMapping("diary-to-story")
    public ResponseEntity<StoryBook> test(@RequestBody BookForm bookForm){
        //동화책 만들기
        StoryBook storyBook = new StoryBook();
        System.out.println(bookForm.getStory_type());
        storyBook.setStory_type(bookForm.getStory_type());
        storyBook.setSubject(bookForm.getSubject());
        storyBook.setDate(bookForm.getDate());
        Long sbId = storyBookService.saveBook(storyBook);
        storyBookService.makePages(bookForm, storyBook);
        return ResponseEntity.ok(storyBook);
    }

}