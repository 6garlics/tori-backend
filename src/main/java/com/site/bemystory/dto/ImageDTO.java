package com.site.bemystory.dto;


import com.site.bemystory.domain.Image;
import lombok.Builder;
import lombok.Data;


public class ImageDTO {

    @Data
    public static class OnlyUrl{
        private String imgUrl;

        public Image toEntity(){
            return Image.builder().imgUrl(this.imgUrl).build();
        }
    }

    @Data
    public static class Response{
        private String imgUrl;
        private int index;

        @Builder
        public Response(String imgUrl, int index) {
            this.imgUrl = imgUrl;
            this.index = index;
        }
    }

}
