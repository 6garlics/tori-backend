package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class BookOneRequest {
    private Long bookId;
    private String userName;
    private String title;
    private String date;
    private String genre;
    private String coverUrl;
    private List<String> texts;
    private List<String> images;


}
