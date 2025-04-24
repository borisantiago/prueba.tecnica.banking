package com.prueba.tecnica.banking.exception;

import lombok.Getter;

@Getter
public class NoAcceptableException extends RuntimeException{
    private final String code;

    public NoAcceptableException(String code, String message){
        super(message);
        this.code = code;
    }
}
