package com.site.bemystory.service;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.StoryBook;
import com.site.bemystory.repository.MemoryDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class DiaryService {
    private final MemoryDiaryRepository diaryRepository;
    private final WebClient webClient;

    public DiaryService(MemoryDiaryRepository diaryRepository, WebClient webClient) {
        this.diaryRepository = diaryRepository;
        this.webClient = webClient;
    }

    /**
     * 일기 저장
     */
    public Long save(Diary diary){
        diaryRepository.save(diary);
        return diary.getId();
    }

    /**
     * 일기 조회 - 1개
     */
    public Optional<Diary> findOne(Long diaryId){
        return diaryRepository.findById(diaryId);
    }

    /**
     * 일기 조회 - 모두
     */
    public List<Diary> findDiaries(){
        return diaryRepository.findAll();
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


}
