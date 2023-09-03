package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import com.site.bemystory.domain.Diary;
import lombok.*;

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
    public static class Save{
        private Long diaryId;
        private String title;
        private String genre;
        private String coverUrl;
        private String date;
        private List<String> texts;
        private List<String> images;

        public Book toBook(){
            return Book.builder()
                    .title(this.title)
                    .genre(this.genre)
                    .date(date)
                    .build();
        }
    }
}
