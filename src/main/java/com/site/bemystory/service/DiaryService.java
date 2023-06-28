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

    public DiaryService(MemoryDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
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

}
