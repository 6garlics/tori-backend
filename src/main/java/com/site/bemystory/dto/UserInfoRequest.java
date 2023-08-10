package com.site.bemystory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserInfoRequest {
    private String userName;
    private String profileImg;
}
