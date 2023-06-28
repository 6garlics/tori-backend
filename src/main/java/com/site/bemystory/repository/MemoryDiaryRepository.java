package com.site.bemystory.repository;

import com.site.bemystory.domain.Diary;
import lombok.val;

import java.util.*;

public class MemoryDiaryRepository implements DiaryRepository{
    private static Map<Long, Diary> store=new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Diary save(Diary diary) {
        diary.setId(++sequence);
        store.put(sequence, diary);
        return diary;
    }

    @Override
    public Optional<Diary> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Diary> findBySubject(String subject) {
        return store.values().stream()
                .filter(diary -> diary.getSubject().equals(subject))
                .findAny();
    }

    @Override
    public List<Diary> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){store.clear();}
}
