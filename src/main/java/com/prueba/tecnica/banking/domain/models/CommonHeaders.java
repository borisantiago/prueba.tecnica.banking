package com.prueba.tecnica.banking.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonHeaders {
    private String device;
    private String deviceIp;
    private String session;
    private String guid;
}
