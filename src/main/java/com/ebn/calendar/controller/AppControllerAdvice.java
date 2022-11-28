package com.ebn.calendar.controller;

import com.ebn.calendar.model.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class AppControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<MessageResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return result.getFieldErrors().stream()
                .map(this::messageFromError)
                .toList();
    }

    private MessageResponse messageFromError(FieldError fieldError) {
        String message = fieldError.getDefaultMessage();
        if (message == null) {
            message = "";
        }
        return new MessageResponse(message);
    }
}