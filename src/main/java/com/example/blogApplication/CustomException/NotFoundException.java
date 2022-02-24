package com.example.blogApplication.CustomException;

import org.springframework.http.HttpStatus;

public class NotFoundException{
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
