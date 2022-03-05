package com.titaniumpanda.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ResourceErrorMessage> requestHandlingNoHandlerFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResourceErrorMessage.resourceErrorMessage());
    }
}
