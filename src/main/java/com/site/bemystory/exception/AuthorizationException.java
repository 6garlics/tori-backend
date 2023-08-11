package com.site.bemystory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorizationException extends Exception{
    private ErrorCode errorCode;
    private String message; //어떤 상황에서 예외가 발생했는지
}
