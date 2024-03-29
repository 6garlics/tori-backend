package com.site.bemystory.service;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.SliceResponse;
import com.site.bemystory.repository.StoryBookRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeLineService {
    private final StoryBookRepository bookRepository;
    private final UserRepository userRepository;

    public SliceResponse processBookExceptNowUser(String userName, Pageable pageable){
        User user = userRepository.findByUserName(userName).orElseThrow();
        Slice<BookDTO.Timeline> slice = bookRepository.findAllByUserNotOrderByBookIdDesc
                (user, pageable);
        return new SliceResponse(slice);

    }
}
