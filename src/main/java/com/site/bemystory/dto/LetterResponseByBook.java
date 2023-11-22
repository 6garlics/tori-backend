package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class LetterResponseByBook {
    private Long letterId;
    private String content;
    private LocalDateTime date;
    private Profile fromUser;
}
