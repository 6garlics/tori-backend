package com.site.bemystory.repository;

import com.site.bemystory.domain.Diary;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository {

    Diary save(Diary diary);
    Optional<Diary> findById(Long id);

    Optional<Diary> findBySubject(String subject);

    List<Diary> findAll();

}
