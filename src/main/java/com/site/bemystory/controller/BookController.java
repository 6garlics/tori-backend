package com.site.bemystory.controller;

import com.site.bemystory.domain.*;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.ImageDTO;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final DiaryService diaryService;

    /**
     * 동화 text 생성 - fastapi
     */
    @ResponseBody
    @GetMapping ("/book")
    public ResponseEntity<BookDTO.OnlyText> makeText(@RequestParam("id") Long id){
        //diary id로 동화로 바꾸려는 일기 조회
        Diary diary = diaryService.findOne(id).get();
        log.info("diary id : {}", diary.getId());

        //fastapi로 보내서 Text만 존재하는 동화책
        BookDTO.ForAI response = bookService.getText(diary.toDTO());
        return ResponseEntity.ok(bookService.saveBook(response, diary));

    }

    /**
     * Cover 생성
     */
    @ResponseBody
    @GetMapping("/books/{bookId}/cover")
    public ResponseEntity<String> cover(@PathVariable Long bookId) throws IOException {
        return ResponseEntity.ok(bookService.getCover(bookService.findOneForAI(bookId).get(), bookId));
    }

    /**
     * 일러스트 1개 생성
     */
    @ResponseBody
    @GetMapping("/books/{bookId}/pages/{pageNum}")
    public ResponseEntity<ImageDTO.Response> page(@PathVariable Long bookId, @PathVariable int pageNum) throws IOException {
        return ResponseEntity.ok(bookService.getIllust(bookId, pageNum).toDTO());
    }

    /**
     * 내 책장
     */
    @ResponseBody
    @GetMapping("/users/{userId}/books")
    public ResponseEntity<List<BookDTO.BookShelf>> showBooks(@PathVariable Long userId){
        List<BookDTO.BookShelf> books = new ArrayList<>();
        int i = 0;
        List<Book> fbooks = bookService.findBooks(userId);
        log.info("총 {}권", fbooks.size());
        for(Book book : fbooks){
            BookDTO.BookShelf b = book.toDTO();
            books.add(book.toDTO());
            books.get(i++).setCoverUrl(bookService.findCover(book));
        }
        return ResponseEntity.ok(books);
    }



    @ResponseBody
    @GetMapping("/booktest")
    public ResponseEntity<Diary> test(@RequestParam("id") Long id){
        //diary id로 동화로 바꾸려는 일기 조회
        Diary diary = diaryService.findOne(id).get();
        return ResponseEntity.ok(diary);
    }


}