package com.site.bemystory.controller;

import com.site.bemystory.domain.*;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
public class BookController {

    private final BookService bookService;
    private final DiaryService diaryService;

    private final BookRepository bookRepository;
    @Autowired
    public BookController(BookService bookService, DiaryService diaryService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.diaryService = diaryService;
        this.bookRepository = bookRepository;
    }

    /**
     * 동화 text 생성 - fastapi
     */
    @ResponseBody
    @GetMapping ("/book")
    public ResponseEntity<BookDTO.OnlyText> makeText(@RequestParam("id") Long id){
        //diary id로 동화로 바꾸려는 일기 조회
        Diary diary = diaryService.findOne(id).get();
        System.out.println(diary.getId());

        //fastapi로 보내서 Text만 존재하는 동화책
        BookDTO.AIResponse response = bookService.passToAI(diary.toDTO());
        return ResponseEntity.ok(bookService.saveBook(response, diary));


        /*//동화책 만들기
        StoryBook storyBook = new StoryBook();
        storyBook.setStory_type(bookForm.getStory_type());
        storyBook.setSubject(bookForm.getSubject());
        storyBook.setDate(bookForm.getDate());
        bookService.makePages(bookForm, storyBook);
        bookService.saveBook(storyBook);
        Book book = new Book();
        book.setStoryBook(storyBook);
        book.setPages(bookService.findPage(storyBook));
        return ResponseEntity.ok(book);*/
    }

    @ResponseBody
    @GetMapping("/booktest")
    public ResponseEntity<Diary> test(@RequestParam("id") Long id){
        //diary id로 동화로 바꾸려는 일기 조회
        Diary diary = diaryService.findOne(id).get();
        return ResponseEntity.ok(diary);
    }


}