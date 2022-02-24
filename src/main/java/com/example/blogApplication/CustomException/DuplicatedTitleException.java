package com.example.blogApplication.CustomException;

import org.springframework.http.HttpStatus;

public class DuplicatedTitleException {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    private final String message;

    public DuplicatedTitleException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
