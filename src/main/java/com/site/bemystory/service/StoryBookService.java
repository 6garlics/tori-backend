package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.MemoryStoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class StoryBookService {
    private final MemoryStoryRepository storyRepository;
    private final AmazonS3Client amazonS3Client;

    public StoryBookService(MemoryStoryRepository storyRepository, AmazonS3Client amazonS3Client) {
        this.storyRepository = storyRepository;
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
     * fastapi에게 받은 이미지 url S3에 업로드
     */
    private String uploadImage(int seq, String subject, String tmp_url) {
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
    private void setImages(StoryBook storyBook){
        String subject = storyBook.getSubject();
        List<String> img = storyBook.getImg_urls();
        int seq = 0;
        for(String tmp_url : img){
            seq++;
            // 이미지 url s3로 바꿈
            img.set(seq, uploadImage(seq, subject, tmp_url));
        }
    }
}
