package com.example.app.exception.handler;

import com.example.app.exception.ClientErrorException;
import com.example.app.exception.ServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        log.error("Controller Advice called for Exception ", e);
        model.addAttribute("customErrorMessage", e.getMessage());
        return "custom-error.html";
    }

    @ExceptionHandler(ClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleClientError(ClientErrorException e, Model model) {
        log.error("Controller Advice called for Exception ", e);
        model.addAttribute("customErrorMessage", e.getMessage());
        return "custom-error.html";
    }

    @ExceptionHandler({ServerErrorException.class, ResourceAccessException.class,RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerError(RuntimeException e, Model model) {
        log.error("Controller Advice called for Exception ", e);
        model.addAttribute("customErrorMessage", e.getMessage());
        return "custom-error.html";
    }
}
