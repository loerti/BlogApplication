package com.example.blogApplication.CustomException;

public class DuplicatedTitleRequestException  extends RuntimeException{

    public DuplicatedTitleRequestException(String message) {
        super(message);
    }

    public DuplicatedTitleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}