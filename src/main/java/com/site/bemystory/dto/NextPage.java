package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NextPage {
    private String newText;
    private String newImage;
    private int x;
    private int y;
}
