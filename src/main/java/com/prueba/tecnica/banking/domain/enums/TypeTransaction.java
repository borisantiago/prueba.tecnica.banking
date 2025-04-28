package com.prueba.tecnica.banking.domain.enums;

import lombok.Getter;

@Getter
public enum TypeTransaction {
    CREDITO("CREDITO"),
    DEBITO("DEBITO");

    private String value;

    TypeTransaction(String value) {
        this.value = value;
    }
}
