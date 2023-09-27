package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import lombok.*;

import java.util.List;


public class BookDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class BookMeta {
        private Long bookId;
        private String title;
        private String coverUrl;
        private String date;
    }
    @Getter
    @Setter
    public static class Save{
        private Long diaryId;
        private String title;
        private String genre;
        private String coverUrl;
        private String date;
        private List<Page> pages;

        public Book toBook(){
            return Book.builder()
                    .title(this.title)
                    .genre(this.genre)
                    .date(date)
                    .build();
        }
    }
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ResponseId{
        private Long bookId;
    }
}
