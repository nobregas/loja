package com.gabnob.userapi.exception.advice;

import java.time.LocalDateTime;

import com.gabnob.shoppingclient.dtos.ErrorDTO;
import com.gabnob.shoppingclient.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.gabnob.userapi.controller")
public class UserControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDTO handleUserNotFound(
            UserNotFoundException userNotFoundException
    ) {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDTO.setMessage("Usuario nao encontrado");
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }




}