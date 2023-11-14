package com.site.bemystory.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LetterRequest {
    private Long bookId;
    private String content;
}
