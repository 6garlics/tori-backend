package com.site.bemystory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.site.bemystory.domain.*;
import com.site.bemystory.dto.*;

import com.site.bemystory.service.DiaryService;
import com.site.bemystory.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final DiaryService diaryService;


    /**
     * 최초 동화책 저장
     */
    @PostMapping("/books")
    public ResponseEntity<BookDTO.ResponseId> saveBook(Authentication auth, @RequestBody BookDTO.Save request) throws JsonProcessingException {
        return ResponseEntity.ok().body(bookService.saveBook(auth.getName(), request));
    }

    /**
     * 동화책 수정
     */
    @PutMapping("/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable Long bookId, @RequestBody BookUpdate update) {
        bookService.updateBook(bookId, update);
        return ResponseEntity.ok().build();
    }

    /**
     * 뒷이야기 이어쓰기
     */
    @PostMapping("/books/{bookId}")
    public ResponseEntity addPage(@PathVariable Long bookId, @RequestBody AddStory add) {
        bookService.addPage(bookId, add);
        return ResponseEntity.ok().build();
    }

    /**
     * 동화책 삭제
     */
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity delete(@PathVariable Long bookId){
        bookService.delete(bookId);
        return ResponseEntity.ok().build();
    }

    /**
     * 책장 조회
     */
    @ResponseBody
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO.BookMeta>> showBooks(@RequestParam String userName) {
        List<BookDTO.BookMeta> books = new ArrayList<>();
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
                book.toRequest(bookService.makePage(bookId)));
    }


}