package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import com.site.bemystory.utils.ScrollPaginationCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponse {
    private static final long LAST_CURSOR = -1L;

    private List<BookDTO.BookMeta> contents = new ArrayList<>();
    private long totalElements;
    private long nextCursor;

    private FeedResponse(List<BookDTO.BookMeta> contents, long totalElements, long nextCursor){
        this.contents=contents;
        this.totalElements=totalElements;
        this.nextCursor=nextCursor;
    }

    public static FeedResponse of(ScrollPaginationCollection<Book> feedsScroll, long totalElements){
        if(feedsScroll.isLastScroll()){
            return FeedResponse.newLastScroll(feedsScroll.getCurrentScrollItems(), totalElements);
        }
        return FeedResponse.newScrollHasNext(feedsScroll.getCurrentScrollItems(), totalElements, feedsScroll.getNextCursor().getBookId());
    }

    private static FeedResponse newLastScroll(List<Book> feedsScroll, long totalElements) {
        return newScrollHasNext(feedsScroll, totalElements, LAST_CURSOR);
    }

    private static FeedResponse newScrollHasNext(List<Book> feedScroll, long totalElements, long nextCursor){
        return new FeedResponse(getContents(feedScroll), totalElements, nextCursor);
    }
    private static List<BookDTO.BookMeta> getContents(List<Book> feedsScroll){
        return feedsScroll.stream()
                .map(feed-> BookDTO.BookMeta.builder()
                        .bookId(feed.getBookId())
                        .title(feed.getTitle())
                        .coverUrl(feed.getCover().getCoverUrl())
                        .date(feed.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}
