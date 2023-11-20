package com.site.bemystory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LetterList<T> {
    private List<T> list;
}
