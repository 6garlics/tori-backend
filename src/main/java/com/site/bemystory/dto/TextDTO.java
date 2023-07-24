package com.site.bemystory.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TextDTO {
    String text;

    @Builder
    public TextDTO(String text) {
        this.text = text;
    }
}
