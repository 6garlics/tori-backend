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
    private List<BookDTO.BookMeta> transform(List<Book> original){
        List<BookDTO.BookMeta> books = new ArrayList<>();
        for(Book b : original){
            books.add(BookDTO.BookMeta.builder()
                    .bookId(b.getBookId())
                    .title(b.getTitle())
                    .date(b.getDate())
                    .coverUrl(b.getCover().getUrl())
                    .build());

        }
        return books;
    }
    public SliceResponse processBookExceptNowUser(String userName, Pageable pageable){
        User user = userRepository.findByUserName(userName).orElseThrow();
        Slice<BookDTO.BookMeta> slice = bookRepository.findAllByUserNotOrderByBookIdDesc
                (user, pageable);
        SliceResponse response = new SliceResponse(slice);
        return response;

    }
}
