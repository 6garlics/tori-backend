package com.site.bemystory.dto;

import com.site.bemystory.domain.Diary;
import com.site.bemystory.domain.User;
import lombok.*;


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
            return Diary.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .genre(this.genre)
                    .date(this.date)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class AIRequest{
        private String title;
        private String contents;
        private String genre;
    }

}
