package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Image;
import com.site.bemystory.domain.Text;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.ArrayList;


public class BookDTO {
    @Data
    public static class AIResponse{
        private String title;
        private List<String> textList = new ArrayList<>();

        public Book toEntity(){
            return Book.builder()
                    .title(this.title)
                    .build();
        }
    }

    @Data
    public static class OnlyText{
        private Long bookId;
        private String title;
        private String genre;
        private String date;
        private List<String> texts = new ArrayList<>();

        @Builder
        public OnlyText(Long bookId, String title, String genre, String date, List<String> textList) {
            this.bookId = bookId;
            this.title = title;
            this.genre = genre;
            this.date = date;
            this.texts = textList;
        }
    }

    @Data
    public static class Info{
        private String title;
        private String genre;
        private String date;
        private List<Text> texts = new ArrayList<>();
        private List<Image> images = new ArrayList<>();

        @Builder
        public Info(String title, List<Text> texts) {
            this.title = title;
            this.texts = texts;
        }
    }
}
