package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class LetterResponseByUser {
    private Long letterId;
    private String content;
    private LocalDateTime date;
    private Profile fromUser;
    private BookDTO.BookMeta book;
}
