package com.ebn.calendar.controller;

import com.ebn.calendar.model.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public List<MessageResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        return result.getFieldErrors().stream()
                .map(this::messageFromError)
                .toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public MessageResponse methodArgumentNotValidException(HttpMessageNotReadableException exception) {
        return new MessageResponse("fields don't respect type constraints");
    }

    private MessageResponse messageFromError(FieldError fieldError) {
        String message = fieldError.getDefaultMessage();
        if (message == null) {
            message = "";
        }
        return new MessageResponse(message);
    }
}