package com.example.blogApplication.CustomException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class APIExceptionHandler {

    Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(value = APIRequestException.class)
    public ResponseEntity<Object> handleNotFoundException(APIRequestException e) {

        NotFoundException notFoundException = new NotFoundException(e.getMessage());
        logger.error(notFoundException.getMessage());

        return new ResponseEntity<>(notFoundException, notFoundException.getHttpStatus());
    }

    @ExceptionHandler(value = DuplicatedTitleRequestException.class)
    public ResponseEntity<Object> handleDuplicatedTitleException(DuplicatedTitleRequestException e) {

        DuplicatedTitleException exception = new DuplicatedTitleException(e.getMessage());
        logger.error(exception.getMessage());

        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }

}
