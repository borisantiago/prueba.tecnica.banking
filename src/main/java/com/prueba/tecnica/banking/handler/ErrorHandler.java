package com.prueba.tecnica.banking.handler;

import com.prueba.tecnica.banking.domain.ErrorResponse;
import com.prueba.tecnica.banking.exception.BadRequestException;
import com.prueba.tecnica.banking.exception.BankingException;
import com.prueba.tecnica.banking.exception.NotFoundException;
import com.prueba.tecnica.banking.exception.UnavailableException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(BankingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse bankingExceptionHandler(BankingException exception) {
        return ErrorResponse.builder().code(exception.getCode()).description(exception.getMessage()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException e) {
        return ErrorResponse.builder().code(e.getCode()).description(e.getMessage()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestExceptionHandler(BadRequestException e) {
        return ErrorResponse.builder().code(e.getCode()).description(e.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String errorFields = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getField)
                .collect(Collectors.joining(", "));
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.toString())
                .description("Invalid Fields: " + errorFields).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse httpRequestMethodNotSupportedExceptionHandler(HttpMessageNotReadableException exception) {
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.toString())
                .description("Request could not be deserialized.").build();
    }

    @ExceptionHandler({ServerWebInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse serverWebInputException(ServerWebInputException exception) {
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.toString())
                .description(exception.getMessage()).build();
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse webExchangeBindExceptionHandler(WebExchangeBindException exception) {
        String errorFields = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField).collect(Collectors.joining(", "));
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.toString())
                .description("Invalid Fields: " + errorFields).build();
    }

    @ExceptionHandler({UnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse unavailableExceptionHandler(UnavailableException exception) {
        return ErrorResponse.builder().code(HttpStatus.SERVICE_UNAVAILABLE.toString()).description(exception.getMessage()).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse serverConstraintViolationException(ConstraintViolationException exception) {
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.toString())
                .description(exception.getMessage()).build();
    }

}
