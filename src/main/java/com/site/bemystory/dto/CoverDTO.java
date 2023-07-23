package com.site.bemystory.dto;

import com.site.bemystory.domain.Cover;
import lombok.Data;

@Data
public class CoverDTO {
    private String coverUrl;
    public Cover toEntity(){
        return Cover.builder()
                .coverUrl(this.coverUrl)
                .build();
    }
}
