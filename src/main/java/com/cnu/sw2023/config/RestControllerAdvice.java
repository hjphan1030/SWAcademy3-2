package com.cnu.sw2023.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(NoHandlerFoundException.class)
    protected String handleNoHandlerFoundException(NoHandlerFoundException e,
                                                                          HttpServletRequest request) {
        log.info("나 여기있따");
        return "index";
    }
}
