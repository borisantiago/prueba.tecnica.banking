package com.prueba.tecnica.banking.controller;

import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.CustomerService;
import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add-customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer registerCustomer(@RequestBody Customer customer,
                                                       @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9_-]$") String device,
                                                       @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))$") String deviceIp,
                                                       @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]$") String session,
                                                       @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.saveCustomer(customer, commonHeaders);
    }

    @PostMapping("/update-customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer saveCustomer(@RequestBody Customer customer,
                                 @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9_-]$") String device,
                                 @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))$") String deviceIp,
                                 @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]$") String session,
                                 @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.updateCustomer(customer, commonHeaders);
    }

    @PostMapping("/find-customer")
    @ResponseStatus(HttpStatus.OK)
    public CustomerServiceImpl.CustomerResponseDTO findCustomer(@RequestBody Customer customer,
                                                                @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9_-]$") String device,
                                                                @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))$") String deviceIp,
                                                                @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]$") String session,
                                                                @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.findCustomerWithAccountsAndMovements(
                Customer.builder().identification(customer.getIdentification()).build(),
                commonHeaders
        );
    }

    @PostMapping("/find-customer-by-date")
    @ResponseStatus(HttpStatus.OK)
    public CustomerServiceImpl.CustomerResponseDTO findCustomerByDate(
            @RequestBody Customer customer,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9_-]+$") String device,
            @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}$") String deviceIp,
            @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
            @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$") @Size(min = 36, max = 36) String guid
    ) {
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.findCustomerWithMovementsBetweenDates(customer.getIdentification(), startDate, endDate, commonHeaders);
    }
}
