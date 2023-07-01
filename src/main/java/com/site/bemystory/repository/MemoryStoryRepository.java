package com.site.bemystory.repository;

import com.site.bemystory.domain.StoryBook;

import java.util.*;

public class MemoryStoryRepository implements StoryBookRepository{
    private Map<Long, StoryBook> st = new HashMap<>();

    private static long sequence = 0L;
    @Override
    public StoryBook save(StoryBook storyBook) {
        storyBook.setBookId(++sequence);;
        st.put(sequence, storyBook);
        return storyBook;
    }

    @Override
    public Optional<StoryBook> findById(Long id) {
        return Optional.ofNullable(st.get(id));
    }

    @Override
    public Optional<StoryBook> findBySubject(String subject) {
        return st.values().stream()
                .filter(storyBook -> storyBook.getSubject().equals(subject))
                .findAny();

    }

    @Override
    public List<StoryBook> findAll() {
        return new ArrayList<>(st.values());
    }
}
