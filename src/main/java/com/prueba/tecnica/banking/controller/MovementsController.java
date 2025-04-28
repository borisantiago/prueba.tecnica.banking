package com.prueba.tecnica.banking.controller;

import com.prueba.tecnica.banking.domain.entity.Movements;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.MovementsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovementsController {

    private final MovementsService movementsService;

    @PostMapping("/add-movements")
    @ResponseStatus(HttpStatus.OK)
    public Movements registerMovement(@RequestBody Movements movements,
                                    @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                    @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                    @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                    @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return movementsService.saveMovements(movements, commonHeaders);
    }

    @PostMapping("/update-movements")
    @ResponseStatus(HttpStatus.OK)
    public Movements saveMovement(@RequestBody Movements movements,
                                @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return movementsService.updateMovements(movements, commonHeaders);
    }

    @DeleteMapping("/delete-movement")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteMovement(
            @RequestParam String id,
            @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
            @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
            @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
            @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$") @Size(min = 36, max = 36) String guid
    ) {
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        movementsService.deleteMovementForId(id, commonHeaders);
        return ResponseEntity.noContent().build();
    }
}
