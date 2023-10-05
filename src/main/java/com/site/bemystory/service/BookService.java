package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.*;
import com.site.bemystory.dto.AddStory;
import com.site.bemystory.dto.BookDTO;

import com.site.bemystory.dto.BookUpdate;
import com.site.bemystory.dto.Page;
import com.site.bemystory.exception.BookException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.ImageException;
import com.site.bemystory.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final TextRepository textRepository;

    private final ImageRepository imageRepository;
    private final CoverRepository coverRepository;
    private final DiaryRepository diaryRepository;
    private final BookRepository bookRepository;
    private final WebClient webClient;
    private final AmazonS3Client amazonS3Client;
    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * 동화책 저장
     */
    public BookDTO.ResponseId saveBook(String userName, BookDTO.Save dto) {
        //이미 동화가 있는 일기에 대해 또 동화를 만들려고 하면 에러
        bookRepository.findByDiary(dto.getDiaryId()).ifPresent(a -> {
            throw new BookException(ErrorCode.BOOK_DUPLICATED, "이미 동화가 생성된 일기입니다.");
        });

        Book book = dto.toBook();
        book.setUser(userService.findUser(userName));

        book.setDiary(diaryRepository.findById(dto.getDiaryId()).orElseThrow());
        bookRepository.save(book);
        log.info("ID{} book 저장", book.getBookId());
        coverRepository.save(Cover.builder().url(dto.getCoverUrl()).book(book).build());
        List<Page> pages = dto.getPages();
        for (int i = 0; i < pages.size(); i++) {
            textRepository.save(Text.builder()
                    .text(pages.get(i).getText())
                    .index(i)
                    .book(book)
                    .x(pages.get(i).getX())
                    .y(pages.get(i).getY())
                    .build());
            imageRepository.save(Image.builder()
                    .book(book)
                    .imgUrl(pages.get(i).getImgUrl())
                    .index(i)
                    .build());

        }
        return BookDTO.ResponseId.builder().bookId(book.getBookId()).build();
    }

    /**
     * 동화 수정
     */
    public void updateBook(Long bookId, BookUpdate dto) {
        Book selected = bookRepository.findById(bookId).orElseThrow();
        selected.update(dto);
        coverRepository.findByBook(selected).orElseThrow().update(dto);
        List<Page> pages = dto.getPages();
        List<Text> texts = bookRepository.findTextList(bookId);
        List<Image> images = bookRepository.findImageList(bookId);
        for (int i = 0; i < texts.size(); i++) {
            texts.get(i).update(pages.get(i).getText());
            images.get(i).update(pages.get(i).getImgUrl());
        }
    }

    /**
     * 뒷이야기 저장
     */
    public void addPage(Long bookId, AddStory add) {
        Book selected = bookRepository.findById(bookId).orElseThrow();
        List<Text> texts = bookRepository.findTextList(bookId);
        int index = texts.size();
        textRepository.save(Text.builder()
                .text(add.getNewText())
                .book(selected)
                .index(index)
                .x(add.getX())
                .y(add.getY()).build());
        imageRepository.save(Image.builder()
                .imgUrl(add.getNewImage())
                .index(index)
                .book(selected).build());
    }

    /**
     * 동화 삭제
     */
    public void delete(Long bookId) {
        Book selected = bookRepository.findById(bookId).orElseThrow();
        List<Text> texts = bookRepository.findTextList(bookId);
        List<Image> images = bookRepository.findImageList(bookId);
        coverRepository.findByBook(selected).orElseThrow().delete();
        for (int i = 0; i < texts.size(); i++) {
            texts.get(i).delete();
            images.get(i).delete();
        }
        selected.delete();
    }

    /**
     * 동화 조회 - 1개
     */
    public Optional<Book> findOne(Long id) {
        return bookRepository.findById(id);
    }


    /**
     * 동화 조회 - 모두
     */
    public List<Book> findBooks(String userName) {
        User user = userService.findUser(userName);
        return bookRepository.findAll(user.getUser_id());
    }


    /**
     * find cover
     */
    public String findCover(Book book) {
        Cover cover = coverRepository.findByBook(book).get();
        return cover.getUrl();
    }

    /**
     * find texts
     */
    public List<String> findTexts(Book book) {
        return bookRepository.findTexts(book.getBookId());
    }

    /**
     * find images
     */
    public List<String> findImages(Book book) {
        return bookRepository.findImages(book.getBookId());
    }


    /**
     * fastapi에게 받은 이미지 url S3에 업로드
     */
    //Todo : 나중에 이미지 upload함수들 private으로 바꾸기
    public String uploadImage(String tmp_url) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        log.info("temp url= {}", tmp_url);
        String fileUrl = "https://" + bucket + ".s3." + region +
                ".amazonaws.com/" + fileName;


        /*ResponseEntity<byte[]> image = restTemplate.getForEntity(tmp_url, byte[].class);
        if(!image.getStatusCode().is2xxSuccessful()){
            new ImageException(ErrorCode.IMAGE_NOT_FOUND, "이미지가 없습니다.");
        }
        byte[] imageBytes = image.getBody();
        ObjectMetadata metadata = new ObjectMetadata();
        log.info("이미지 길이 {}", imageBytes.length);
        metadata.setContentLength(imageBytes.length);
        //s3에 업로드
        amazonS3Client.putObject(bucket, fileName, new ByteArrayInputStream(imageBytes), metadata);*/

        try {
            //url에서 이미지 추출
            InputStream inputStream = new URL(tmp_url).openStream();

            amazonS3Client.putObject(bucket, fileName, inputStream, null);
        } catch (IOException e) {
            new ImageException(ErrorCode.IMAGE_NOT_FOUND, "이미지가 없습니다.");
        }
        return fileUrl;
    }


    /**
     * 책 주인 찾기
     */
    public String findUser(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        return userService.findById(book.getUser().getUser_id()).orElseThrow().getUserName();
    }

    /**
     * Page dto 생성
     */
    public List<Page> makePage(Long bookId) {
        Book book = findOne(bookId).orElseThrow();
        List<Text> texts = bookRepository.findTextList(bookId);
        List<String> images = findImages(book);
        List<Page> pages = new ArrayList<>();
        for(int i=0;i<texts.size();i++){
            pages.add(Page.builder()
                    .text(texts.get(i).getText())
                    .imgUrl(images.get(i))
                    .x(texts.get(i).getX())
                    .y(texts.get(i).getY())
                    .build());
        }
        return pages;
    }

}
