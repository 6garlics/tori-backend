package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import lombok.*;

import java.util.List;
import java.util.ArrayList;


public class BookDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ForAI {
        private String title;
        private List<String> texts = new ArrayList<>();


        public Book toEntity(){
            return Book.builder()
                    .title(this.title)
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class OnlyText{
        private Long bookId;
        private String title;
        private String genre;
        private String date;
        private List<String> texts = new ArrayList<>();

    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class BookShelf{
        private Long bookId;
        private String title;
        private String coverUrl;
        private String date;
    }
}
