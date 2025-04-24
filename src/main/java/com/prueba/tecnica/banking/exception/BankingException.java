package com.prueba.tecnica.banking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BankingException extends RuntimeException{
    private final String code;

    public BankingException(String code, String message){
        super(message);
        this.code = code;
    }

    public BankingException(){
        super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        this.code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
