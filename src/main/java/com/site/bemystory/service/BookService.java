package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.site.bemystory.domain.*;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.CoverDTO;
import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.dto.ImageDTO;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.ImageException;
import com.site.bemystory.repository.BookRepository;
import com.site.bemystory.repository.CoverRepository;
import com.site.bemystory.repository.ImageRepository;
import com.site.bemystory.repository.TextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
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
    public BookDTO.OnlyText saveBook(BookDTO.ForAI response, Diary diary){
        Book book = response.toEntity();
        book.setDate(diary.getDate());
        book.setGenre(diary.getGenre());
        book.setDiary(diary);
        book.setUser(diary.getUser());
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
    public List<Book> findBooks(Long userId){
        return bookRepository.findAll(userId);
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
    public String getCover(BookDTO.ForAI request, Long bookId) throws IOException {
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

    /**
     * find texts
     */
    public List<String> findTexts(Book book){
        return bookRepository.findTexts(book.getBookId());
    }

    /**
     * find images
     */
    public List<String> findImages(Book book){
        return bookRepository.findImages(book.getBookId());
    }


    public Image getIllust(Long bookId, int index) throws IOException {

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

    public String uploadImage(String tmp_url){
        String fileName = UUID.randomUUID().toString()+".jpg";
        log.info("temp url= {}", tmp_url);
        String fileUrl = "https://" + bucket + ".s3." + region +
                ".amazonaws.com/"+fileName;


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
    public String findUser(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow();
        return userService.findById(book.getUser().getUser_id()).orElseThrow().getUserName();
    }



}
