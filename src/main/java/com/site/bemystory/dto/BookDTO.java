package com.site.bemystory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("titleX")
        private int bookX;
        @JsonProperty("titleY")
        private int bookY;
        private String coverUrl;
        private String date;
    }
    @Getter
    @Setter
    public static class Save{
        private Long diaryId;
        private String title;
        private int titleX;
        private int titleY;
        private String genre;
        private String coverUrl;
        private String date;
        private List<Page> pages;

        public Book toBook(){
            return Book.builder()
                    .title(this.title)
                    .genre(this.genre)
                    .bookX(titleX)
                    .bookY(titleY)
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
