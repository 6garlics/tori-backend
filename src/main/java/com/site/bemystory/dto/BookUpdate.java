package com.site.bemystory.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookUpdate {
    private String title;
    private String coverUrl;
    private List<String> texts;
    private List<String> images;
}
