package com.site.bemystory.service;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.repository.JpaDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DiaryService {
    private final JpaDiaryRepository diaryRepository;

    public DiaryService(JpaDiaryRepository diaryRepository) {
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
