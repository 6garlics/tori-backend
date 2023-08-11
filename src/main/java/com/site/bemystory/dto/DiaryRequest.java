package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class DiaryRequest {
    public String date;
    public String title;
    public String contents;
    public String genre;
}
