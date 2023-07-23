package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.Text;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.TextRepository;
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
public class BookService {
    private final TextRepository textRepository;
    private final BookRepository bookRepository;
    private final WebClient webClient;
    private final AmazonS3Client amazonS3Client;

    public BookService(TextRepository textRepository, BookRepository bookRepository, WebClient webClient, AmazonS3Client amazonS3Client) {
        this.textRepository = textRepository;
        this.bookRepository = bookRepository;
        this.webClient = webClient;
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * 동화책 저장
     */
    public BookDTO.OnlyText saveBook(BookDTO.AIResponse response, Diary diary){
        Book book = response.toEntity();
        book.setDate(diary.getDate());
        book.setGenre(diary.getGenre());
        int i=0;
        for(String text : response.getTextList()){
            textRepository.save(
                    Text.builder()
                    .index(i++)
                    .text(text)
                    .book(book).build());

        }
        bookRepository.save(book);
        return BookDTO.OnlyText.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .date(book.getDate())
                .textList(response.getTextList())
                .build();
    }

    /**
     * 동화 조회 - 1개
     */
    public Optional<Book> findOne(Long id){
        return bookRepository.findById(id);
    }

    /**
     * 동화 조회 - 모두
     */
    public List<Book> findBooks(){
        return bookRepository.findAll();
    }

    //TODO: 동화책 수정


    /**
     * 일기를 chatGPT에게 넘겨주고 Text 받아옴
     */
    public BookDTO.AIResponse passToAI(DiaryDTO.AIRequest request){
        // request api
        return webClient.post()
                .uri("/diaryToStory")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BookDTO.AIResponse.class)
                .block();
    }




    /**
     * fastapi에게 받은 이미지 url S3에 업로드
     */
    //Todo : 나중에 이미지 upload함수들 private으로 바꾸기

    public String uploadImage(String tmp_url) {
        String fileName = UUID.randomUUID().toString()+".jpg";
        String fileUrl = "https://" + bucket + ".s3." + region +
                ".amazonaws.com/"+fileName;

        //url에서 이미지 추출
        InputStream inputStream = null;

        //s3에 업로드
        try {
            inputStream = new URL(tmp_url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            amazonS3Client.putObject(bucket, fileName, inputStream, null);
        }
        return fileUrl;
    }

    /**
     * S3 이미지 URL insert
     */
    /*@Transactional
    public void setImages(StoryBook storyBook){
        List<Page> pages = bookRepository.findPages(storyBook);
        for(Page p : pages){
            // 이미지 url s3로 바꿈
            p.setImg_url(uploadImage(p.getImg_url()));
        }
    }*/


}
