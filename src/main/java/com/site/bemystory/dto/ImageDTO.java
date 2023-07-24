package com.site.bemystory.dto;


import com.site.bemystory.domain.Image;
import lombok.Data;

@Data
public class ImageDTO {
    private String imgUrl;

    public Image toEntity(){
        return Image.builder().imgUrl(this.imgUrl).build();
    }
}
