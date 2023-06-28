package com.site.bemystory.service;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.repository.JpaDiaryRepository;
import com.site.bemystory.repository.MemoryDiaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceTest {
    DiaryService diaryService;
    JpaDiaryRepository diaryRepository;
    WebClient webClient;

    @BeforeEach
    public void beforeEach(){
        //diaryRepository=new JpaDiaryRepository(em);
        webClient = WebClient.builder().baseUrl("https://nkk4y3gpizy3q2h25fiae7e4ma0fezqg.lambda-url.eu-north-1.on.aws").build();
        diaryService = new DiaryService(diaryRepository);
    }

    //@AfterEach
    /*public void afterEach(){
        diaryRepository.clearStore();
    }*/

    @Test
    void save() {

    }

    @Test
    void findOne() {
    }

    @Test
    void findDiaries() {
    }

    @Test
    void passToAI() throws ParseException {
        //given
        Diary diary = new Diary();
        diary.setSubject("자전거");
        diary.setContents("오늘 밤에 자전거를 탔다.\n" +
                "자전거는 처음 탈 때는 좀 중심잡기가 힘들었다.\n" +
                "그러나 재미있었다.\n" +
                "자전거를 잘 타서 엄마, 아빠 산책 갈 때 나도 가야겠다.");
        diary.setDate(LocalDate.now());
        diary.setStory_type("판타지");

        diaryService.save(diary);
        //when
        //diaryService.passToAI(diary);
        //then
    }
}