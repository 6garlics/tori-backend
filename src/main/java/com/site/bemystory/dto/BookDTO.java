package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Image;
import com.site.bemystory.domain.Text;
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
    public static class BookShelf{
        private Long bookId;
        private String title;
        private String coverUrl;

        @Builder
        public BookShelf(Long bookId, String title) {
            this.bookId = bookId;
            this.title = title;
        }
    }
}
