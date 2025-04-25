package com.prueba.tecnica.banking.service.impl;

import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.exception.BankingException;
import com.prueba.tecnica.banking.repository.CustomerRepository;
import com.prueba.tecnica.banking.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponseDTO> findCustomers(CommonHeaders headers) {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(this::mapToBasicResponseDTO)
                .toList();
    }

    @Override
    public Customer saveCustomer(Customer customer, CommonHeaders commonHeaders) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer, CommonHeaders commonHeaders) {
        Customer user = customerRepository.findByIdentification(customer.getIdentification())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "customer not found"));
        user.setCustomerId(customer.getCustomerId());
        user.setPassword(customer.getPassword());
        user.setStatus(customer.getStatus());
        user.setIdentification(customer.getIdentification());
        user.setName(customer.getName());
        user.setGender(customer.getGender());
        user.setAge(customer.getAge());
        user.setAddress(customer.getAddress());
        user.setPhone(customer.getPhone());
        return customerRepository.save(user);
    }

    @Transactional
    @Override
    public CustomerResponseDTO findCustomerWithAccountsAndMovements(Customer customerRequest, CommonHeaders commonHeaders) {
        Customer customer = customerRepository.findWithAccountsByIdentification(customerRequest.getIdentification())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "Customer not found"));
        customer.getAccounts().forEach(account -> account.getMovements().size());
        return mapToResponseDTO(customer);
    }

    @Transactional
    @Override
    public CustomerResponseDTO findCustomerWithMovementsBetweenDates(String identification, LocalDate start, LocalDate end, CommonHeaders commonHeaders) {
        Customer customer = customerRepository.findWithAccountsByIdentification(identification)
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "Customer not found"));

        return mapToResponseDTO(customer, start, end);
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        List<AccountDTO> accountDTOs = customer.getAccounts().stream().map(account -> {
            List<MovementsDTO> movements = account.getMovements().stream().map(movement -> {
                MovementsDTO dto = new MovementsDTO();
                dto.setDate(movement.getDate());
                dto.setAmount(movement.getAmount());
                dto.setMovementType(movement.getMovementType());
                return dto;
            }).toList();

            AccountDTO acc = new AccountDTO();
            acc.setAccountNumber(account.getAccountNumber());
            acc.setAccountType(account.getAccountType());
            acc.setBalance(account.getBalance());
            acc.setMovements(movements);
            return acc;
        }).toList();

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setIdentification(customer.getIdentification());
        dto.setName(customer.getName());
        dto.setAccounts(accountDTOs);
        return dto;
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer, LocalDate start, LocalDate end) {
        List<AccountDTO> accountDTOs = customer.getAccounts().stream().map(account -> {
            List<MovementsDTO> filtered = account.getMovements().stream()
                    .filter(m -> m.getDate() != null && !m.getDate().isBefore(start) && !m.getDate().isAfter(end))
                    .map(movement -> {
                        MovementsDTO dto = new MovementsDTO();
                        dto.setDate(movement.getDate());
                        dto.setAmount(movement.getAmount());
                        dto.setMovementType(movement.getMovementType());
                        dto.setBalance(movement.getBalance());
                        return dto;
                    }).toList();

            AccountDTO dto = new AccountDTO();
            dto.setAccountNumber(account.getAccountNumber());
            dto.setAccountType(account.getAccountType());
            dto.setBalance(account.getBalance());
            dto.setMovements(filtered);
            return dto;
        }).toList();

        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setIdentification(customer.getIdentification());
        response.setName(customer.getName());
        response.setAccounts(accountDTOs);
        return response;
    }

    private CustomerResponseDTO mapToBasicResponseDTO(Customer customer) {
        List<AccountDTO> accounts = customer.getAccounts() != null
                ? customer.getAccounts().stream().map(account -> {
            AccountDTO dto = new AccountDTO();
            dto.setAccountNumber(account.getAccountNumber());
            dto.setAccountType(account.getAccountType());
            dto.setBalance(account.getBalance());
            return dto;
        }).toList()
                : List.of();

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setIdentification(customer.getIdentification());
        dto.setName(customer.getName());
        dto.setAccounts(accounts);
        return dto;
    }

    @Data
    public static class CustomerResponseDTO {
        private String identification;
        private String name;
        private List<AccountDTO> accounts;
    }

    @Data
    public class AccountDTO {
        private Long accountNumber;
        private Double balance;
        private String accountType;
        private List<MovementsDTO> movements;
    }

    @Data
    public class MovementsDTO {
        private String movementType;
        private Double amount;
        private LocalDate date;
        private Double balance;
    }

}
