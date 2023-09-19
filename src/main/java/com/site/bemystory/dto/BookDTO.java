package com.site.bemystory.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Diary;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class BookDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class BookShelf{
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
