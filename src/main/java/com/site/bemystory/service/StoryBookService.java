package com.site.bemystory.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.site.bemystory.domain.BookForm;
import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.Page;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.JpaStoryBookRepository;
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
public class StoryBookService {
    private final JpaStoryBookRepository storyRepository;
    private final WebClient webClient;
    private final AmazonS3Client amazonS3Client;

    public StoryBookService(JpaStoryBookRepository storyRepository, WebClient webClient, AmazonS3Client amazonS3Client) {
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
    public StoryBook saveBook(StoryBook storyBook){
        this.setImages(storyBook);
        storyRepository.save(storyBook);
        return storyBook;
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
        StoryBook storyBook = this.findOne(revisedBook.getBookId()).get();

    }

    /**
     * 일기를 chatGPT에게 넘겨주고 StoryBook 받아옴
     */
    public BookForm passToAI(Diary diary){
        // request api
        return webClient.post()
                .uri("/storybook")
                .bodyValue(diary)
                .retrieve()
                .bodyToMono(BookForm.class)
                .block();
    }

    /**
     * BookForm->StoryBook 만들 때, page 생성
     */
    public void makePages(BookForm bookForm, StoryBook storyBook){
        List<String> para = bookForm.getParagraphs();
        List<String> urls = bookForm.getImg_urls();
        int index = para.size();
        for(int i=0 ; i<index ; i++){
            Page p = new Page();
            p.setIdx(i);
            p.setText(para.get(i));
            p.setImg_url(urls.get(i));
            p.setStoryBook(storyBook);
            storyRepository.savePage(p);
            storyBook.addPage(p);
        }
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
    @Transactional
    public void setImages(StoryBook storyBook){
        List<Page> pages = storyRepository.findPages(storyBook);
        for(Page p : pages){
            // 이미지 url s3로 바꿈
            p.setImg_url(uploadImage(p.getImg_url()));
        }
    }

    /**
     * page 찾기
     */
    public List<Page> findPage(StoryBook storyBook){
        return storyRepository.findPages(storyBook);
    }
}
