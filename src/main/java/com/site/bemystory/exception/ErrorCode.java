package com.site.bemystory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_AUTHORIZATION(HttpStatus.UNAUTHORIZED, ""),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, ""),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, ""),
    FOLLOW_DUPLICATED(HttpStatus.CONFLICT, ""),
    BOOK_DUPLICATED(HttpStatus.CONFLICT, "")
    ;
    private HttpStatus httpStatus;
    private String message;
}
