package com.prueba.tecnica.banking.controller;

import com.prueba.tecnica.banking.domain.CustomerMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerServiceImpl.CustomerResponseDTO> findCustomers(@RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                                                       @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                                                       @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                                                       @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.findCustomers(commonHeaders);
    }

    @PostMapping("/add-customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer registerCustomer(@RequestBody @Valid Customer customer,
                                     @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                     @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                     @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                     @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.saveCustomer(customer, commonHeaders);
    }

    @PostMapping("/update-customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer saveCustomer(@RequestBody @Valid Customer customer,
                                 @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                 @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                 @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                 @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.updateCustomer(customer, commonHeaders);
    }

    @PostMapping("/find-customer")
    @ResponseStatus(HttpStatus.OK)
    public CustomerServiceImpl.CustomerResponseDTO findCustomer(@RequestBody Customer customer,
                                                                @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                                                @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                                                @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                                                @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Invalid GUID format") @Size(min = 36, max = 36, message = "GUID must be 36 characters") String guid){
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.findCustomerWithAccountsAndMovements(
                Customer.builder().identification(customer.getIdentification()).build(),
                commonHeaders
        );
    }

    @GetMapping("/find-customer-by-date")
    @ResponseStatus(HttpStatus.OK)
    public CustomerMapper.CustomerResponseMapper findCustomerByDate(@RequestParam String id,
                                                                    @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                    @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                                                    @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                                                    @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                                                    @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$") @Size(min = 36, max = 36) String guid
    ) {
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        return customerService.findCustomerWithMovementsBetweenDates(id, startDate, endDate, commonHeaders);
    }

    @DeleteMapping("/delete-customer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteCustomer(@RequestParam String id,
                                               @RequestHeader("x-device") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String device,
                                               @RequestHeader("x-device-ip") @Valid @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,3}){3}$") String deviceIp,
                                               @RequestHeader("x-session") @Valid @Pattern(regexp = "^[a-zA-Z0-9]+$") String session,
                                               @RequestHeader("x-guid") @Valid @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$") @Size(min = 36, max = 36) String guid
    ) {
        CommonHeaders commonHeaders = new CommonHeaders(device, deviceIp, session, guid);
        customerService.deleteCustomerForId(id, commonHeaders);
        return ResponseEntity.noContent().build();
    }
}
