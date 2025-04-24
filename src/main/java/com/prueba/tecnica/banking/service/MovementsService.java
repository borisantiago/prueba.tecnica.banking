package com.prueba.tecnica.banking.service;

import com.prueba.tecnica.banking.domain.entity.Movements;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;

public interface MovementsService {
    Movements saveMovements(Movements movements, CommonHeaders commonHeaders);
    Movements updateMovements(Movements movements, CommonHeaders commonHeaders);
}
