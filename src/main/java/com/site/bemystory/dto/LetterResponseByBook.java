package com.site.bemystory.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LetterResponseByBook {
    private Long letterId;
    private String content;
    private LocalDateTime date;
    private Profile fromUser;
}
