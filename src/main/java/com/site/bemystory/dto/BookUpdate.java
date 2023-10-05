package com.site.bemystory.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookUpdate {
    private String title;
    private int titleX;
    private int titleY;
    private String coverUrl;
    private List<Page> pages;
}
