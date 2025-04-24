package com.prueba.tecnica.banking.exception;

import lombok.Getter;

@Getter
public class UnavailableException extends RuntimeException {
    private final String code;

    public UnavailableException(String code, String message){
        super(message);
        this.code = code;
    }
}
