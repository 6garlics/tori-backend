package com.site.bemystory.dto;


import com.site.bemystory.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


public class ImageDTO {

    @Data
    public static class OnlyUrl{
        private String imgUrl;

        public Image toEntity(){
            return Image.builder().imgUrl(this.imgUrl).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String imgUrl;
        private int index;
    }

}
