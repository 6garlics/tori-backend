package com.site.bemystory.dto;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;


public class DiaryDTO {

    /**
     * front에서 받아오는 diary
     */
    @Data
    public static class Request{
        private String title;
        private String contents;
        private String genre;
        private String date;
        private User user;


        public Diary toEntity(User user){
            System.out.println("왜"+this.title);
            return Diary.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .genre(this.genre)
                    .date(this.date)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class AIRequest{
        private String title;
        private String contents;
        private String genre;

        @Builder
        public AIRequest(String title, String contents, String genre) {
            this.title = title;
            this.contents = contents;
            this.genre = genre;
        }
    }

}
