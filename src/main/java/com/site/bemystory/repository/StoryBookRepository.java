package com.site.bemystory.repository;

import com.site.bemystory.domain.Page;
import com.site.bemystory.domain.StoryBook;

import java.util.List;
import java.util.Optional;

public interface StoryBookRepository {

    StoryBook save(StoryBook storyBook);
    Optional<StoryBook> findById(Long id);
    Optional<StoryBook> findBySubject(String subject);
    List<StoryBook> findAll();


}
