package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.*;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.CoverDTO;
import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.dto.ImageDTO;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.CoverRepository;
import com.site.bemystory.repository.ImageRepository;
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

    private final ImageRepository imageRepository;
    private final CoverRepository coverRepository;
    private final BookRepository bookRepository;
    private final WebClient webClient;
    private final AmazonS3Client amazonS3Client;

    public BookService(TextRepository textRepository, ImageRepository imageRepository, CoverRepository coverRepository, BookRepository bookRepository, WebClient webClient, AmazonS3Client amazonS3Client) {
        this.textRepository = textRepository;
        this.imageRepository = imageRepository;
        this.coverRepository = coverRepository;
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
    public BookDTO.OnlyText saveBook(BookDTO.ForAI response, Diary diary){
        Book book = response.toEntity();
        book.setDate(diary.getDate());
        book.setGenre(diary.getGenre());
        book.setDiary(diary);
        bookRepository.save(book);
        int i=0;
        for(String text : response.getTexts()){
            textRepository.save(Text.builder()
                    .index(i++)
                    .text(text)
                    .book(book).build());

        }
        return BookDTO.OnlyText.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .date(book.getDate())
                .textList(response.getTexts())
                .build();
    }

    /**
     * 동화 조회 - 1개
     */
    public Optional<Book> findOne(Long id){
        return bookRepository.findById(id);
    }

    public Optional<BookDTO.ForAI> findOneForAI(Long id){
        Book book = bookRepository.findById(id).get();
        List<String> texts = bookRepository.findTexts(id);
        return Optional.ofNullable(BookDTO.ForAI.builder()
                .title(book.getTitle())
                .texts(texts)
                .build());
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
    public BookDTO.ForAI getText(DiaryDTO.AIRequest request){
        // request api
        return webClient.post()
                .uri("/diaryToStory")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BookDTO.ForAI.class)
                .block();
    }

    /**
     * Cover fastapi에 요청하고 저장
     */
    public String  getCover(BookDTO.ForAI request, Long bookId){
        Cover cover = webClient.post()
                .uri("/cover")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CoverDTO.class)
                .block()
                .toEntity();
        cover.setCoverUrl(uploadImage(cover.getCoverUrl()));
        cover.setBook(findOne(bookId).get());
        coverRepository.save(cover);
        return cover.getCoverUrl();
    }

    /**
     * find cover
     */
    public String findCover(Book book){
        Cover cover = coverRepository.findByBook(book).get();
        return cover.getCoverUrl();
    }


    public Image getIllust(Long bookId, int index){

        Image image = webClient.post()
                .uri("/textToImage")
                .bodyValue(bookRepository.findText(bookId, index))
                .retrieve()
                .bodyToMono(ImageDTO.OnlyUrl.class)
                .block()
                .toEntity();
        image.setIndex(index);
        image.setBook(findOne(bookId).get());
        image.setImgUrl(uploadImage(image.getImgUrl()));
        return imageRepository.save(image);
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




}
