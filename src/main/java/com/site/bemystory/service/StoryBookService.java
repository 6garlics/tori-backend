package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.MemoryStoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
public class StoryBookService {
    private final MemoryStoryRepository storyRepository;
    private final WebClient webClient;
    private final AmazonS3Client amazonS3Client;

    public StoryBookService(MemoryStoryRepository storyRepository, WebClient webClient, AmazonS3Client amazonS3Client) {
        this.storyRepository = storyRepository;
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
    public Long saveBook(StoryBook storyBook){
        this.setImages(storyBook);
        storyRepository.save(storyBook);
        return storyBook.getId();
    }

    /**
     * 동화 조회 - 1개
     */
    public Optional<StoryBook> findOne(Long sbId){
        return storyRepository.findById(sbId);
    }

    /**
     * 동화 조회 - 모두
     */
    public List<StoryBook> findStoryBooks(){
        return storyRepository.findAll();
    }

    /**
     * 동화 text 수정
     */
    public void revise(StoryBook revisedBook){
        StoryBook storyBook = this.findOne(revisedBook.getId()).get();

    }

    /**
     * 일기를 chatGPT에게 넘겨주고 StoryBook 받아옴
     */
    public StoryBook passToAI(Diary diary){
        // request api
        return webClient.post()
                .uri("/storybook")
                .bodyValue(diary)
                .retrieve()
                .bodyToMono(StoryBook.class)
                .block();
    }


    /**
     * fastapi에게 받은 이미지 url S3에 업로드
     */
    //Todo : 나중에 이미지 upload함수들 private으로 바꾸기
    public String uploadImage(int seq, String subject, String tmp_url) {
        String fileName = subject + "_" + seq + ".jpg";
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
    public void setImages(StoryBook storyBook){
        String subject = storyBook.getSubject();
        List<String> img = storyBook.getImage_urls();
        int seq = 0;
        for(String tmp_url : img){
            // 이미지 url s3로 바꿈
            img.set(seq, uploadImage(seq, subject, tmp_url));
            seq++;
        }
    }

}
