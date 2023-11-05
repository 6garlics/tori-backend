package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReplyOne {
    private Long replyId;
    private UserInfoRequest rwriter;
    private String content;
    private String date;
    private Long grp;
    private Long grps;
    private Integer grpl;
}
