package com.site.bemystory.controller;

import com.site.bemystory.domain.*;
import com.site.bemystory.dto.*;

import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;


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
     * 책장 조회
     */
    @ResponseBody
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO.BookShelf>> showBooks(@RequestParam String userName) {
        List<BookDTO.BookShelf> books = new ArrayList<>();
        int i = 0;
        List<Book> fbooks = bookService.findBooks(userName);
        for (Book book : fbooks)
            books.add(book.toDTO(bookService.findCover(book)));

        return ResponseEntity.ok().body(books);
    }

    /**
     * 동화책 1개 조회
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookOneRequest> showBook(@PathVariable Long bookId) {
        Book book = bookService.findOne(bookId).orElseThrow();
        return ResponseEntity.ok().body(
                book.toRequest(bookService.findTexts(book), bookService.findImages(book)));
    }

}