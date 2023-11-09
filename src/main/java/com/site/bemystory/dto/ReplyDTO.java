package com.site.bemystory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReplyDTO {
    private Long grp;
    private Long grps;
    private String content;
}
