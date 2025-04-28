//package com.prueba.tecnica.banking.service;
//
//import com.prueba.tecnica.banking.domain.CustomerMapper;
//import com.prueba.tecnica.banking.domain.entity.Account;
//import com.prueba.tecnica.banking.domain.entity.Customer;
//import com.prueba.tecnica.banking.domain.entity.Movements;
//import com.prueba.tecnica.banking.domain.models.CommonHeaders;
//import com.prueba.tecnica.banking.exception.BankingException;
//import com.prueba.tecnica.banking.repository.AccountRepository;
//import com.prueba.tecnica.banking.repository.CustomerRepository;
//import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//public class CustomerServiceImplTest {
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//
//    @Test
//    void shouldReturnListOfCustomersSuccessfully() {
//        // Arrange
//        Customer customer1 = new Customer();
//        customer1.setName("John Doe");
//        customer1.setIdentification("1234567890");
//
//        Customer customer2 = new Customer();
//        customer2.setName("Jane Smith");
//        customer2.setIdentification("0987654321");
//
//        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
//
//        // Act
//        List<CustomerServiceImpl.CustomerResponseDTO> result = customerService.findCustomers(new CommonHeaders());
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("John Doe", result.get(0).getName());
//        assertEquals("Jane Smith", result.get(1).getName());
//
//        verify(customerRepository).findAll();
//    }
//
//
//    @Test
//    void shouldSaveCustomerSuccessfully() {
//        // Arrange
//        Customer customerToSave = new Customer();
//        customerToSave.setIdentification("1234567890");
//        customerToSave.setName("John Doe");
//        customerToSave.setStatus(true);
//
//        when(customerRepository.save(any(Customer.class))).thenReturn(customerToSave);
//
//        CommonHeaders headers = new CommonHeaders("android", "192.168.1.1", "abc123", "550e8777-e29b-41d4-a716-446655440000");
//
//        // Act
//        Customer savedCustomer = customerService.saveCustomer(customerToSave, headers);
//
//        // Assert
//        assertNotNull(savedCustomer);
//        assertEquals("1234567890", savedCustomer.getIdentification());
//        assertEquals("John Doe", savedCustomer.getName());
//        assertTrue(savedCustomer.getStatus());
//
//        verify(customerRepository, times(1)).save(any(Customer.class)); // Verifica que sí se llamó
//    }
//
//    @Test
//    void shouldUpdateCustomerSuccessfully() {
//        // Arrange
//        Customer existingCustomer = new Customer();
//        existingCustomer.setIdentification("1234567890");
//
//        Customer updatedCustomer = new Customer();
//        updatedCustomer.setIdentification("1234567890");
//        updatedCustomer.setName("New Name");
//        updatedCustomer.setAge(30);
//        updatedCustomer.setGender("M");
//        updatedCustomer.setAddress("New Address");
//        updatedCustomer.setPhone("0999999999");
//        updatedCustomer.setPassword("newpassword");
//        updatedCustomer.setStatus(true);
//
//        when(customerRepository.findByIdentification("1234567890")).thenReturn(Optional.of(existingCustomer));
//        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        Customer result = customerService.updateCustomer(updatedCustomer, new CommonHeaders());
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("New Name", result.getName());
//        assertEquals(30, result.getAge());
//        assertEquals("M", result.getGender());
//        assertEquals("New Address", result.getAddress());
//        assertEquals("0999999999", result.getPhone());
//        assertEquals("newpassword", result.getPassword());
//        assertTrue(result.getStatus());
//
//        verify(customerRepository).findByIdentification("1234567890");
//        verify(customerRepository).save(any(Customer.class));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenCustomerNotFound() {
//        // Arrange
//        Customer updatedCustomer = new Customer();
//        updatedCustomer.setIdentification("0000000000");
//
//        when(customerRepository.findByIdentification("0000000000")).thenReturn(Optional.empty());
//
//        // Act & Assert
//        BankingException exception = assertThrows(BankingException.class, () -> {
//            customerService.updateCustomer(updatedCustomer, new CommonHeaders());
//        });
//
//        assertEquals("404 NOT_FOUND", exception.getCode());
//        assertEquals("customer not found", exception.getMessage());
//
//        verify(customerRepository).findByIdentification("0000000000");
//        verify(customerRepository, never()).save(any(Customer.class));
//    }
//
//    @Test
//    void shouldFindCustomerWithMovementsBetweenDatesSuccessfully() {
//        // Arrange
//        Customer customerEntity = new Customer();
//        customerEntity.setIdentification("1234567890");
//
//        when(customerRepository.findWithAccountsByIdentification("1234567890"))
//                .thenReturn(Optional.of(customerEntity));
//
//        // Act
//        CustomerMapper.CustomerResponseMapper response = customerService.findCustomerWithMovementsBetweenDates(
//                "1234567890",
//                LocalDate.of(2024, 1, 1),
//                LocalDate.of(2024, 12, 31),
//                new CommonHeaders()
//        );
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("1234567890", response.getIdentification());
//        verify(customerRepository).findWithAccountsByIdentification("1234567890");
//    }
//
//    @Test
//    void shouldDeleteCustomerSuccessfully() {
//        // Arrange
//        Customer customerEntity = new Customer();
//        customerEntity.setIdentification("1234567890");
//
//        Account account1 = new Account();
//        account1.setAccountNumber(1L);
//        Account account2 = new Account();
//        account2.setAccountNumber(2L);
//
//        customerEntity.setAccounts(List.of(account1, account2));
//
//        when(customerRepository.findWithAccountsByIdentification("1234567890"))
//                .thenReturn(Optional.of(customerEntity));
//
//        // Act
//        customerService.deleteCustomerForId("1234567890", new CommonHeaders());
//
//        // Assert
//        verify(accountRepository, times(1)).deleteById(1L);
//        verify(accountRepository, times(1)).deleteById(2L);
//        verify(customerRepository, times(1)).save(customerEntity);
//
//        assertFalse(customerEntity.getStatus());
//        assertNull(customerEntity.getAccounts());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenDeletingNonexistentCustomer() {
//        // Arrange
//        when(customerRepository.findWithAccountsByIdentification("0000000000"))
//                .thenReturn(Optional.empty());
//
//        // Act & Assert
//        BankingException exception = assertThrows(BankingException.class, () ->
//                customerService.deleteCustomerForId("0000000000", new CommonHeaders())
//        );
//
//        assertEquals(HttpStatus.NOT_FOUND.toString(), exception.getCode());
//        assertEquals("Customer not found", exception.getMessage());
//
//        verify(accountRepository, never()).deleteById(anyLong());
//        verify(customerRepository, never()).save(any(Customer.class));
//    }
//
//    @Test
//    void shouldMapCustomerToCustomerResponseDTO() {
//        // Arrange
//        Movements movement = new Movements();
//        movement.setId(1L);
//        movement.setAmount(100.0);
//        movement.setBalance(500.0);
//        movement.setMovementType("CREDITO");
//
//        Account account = new Account();
//        account.setAccountNumber(12345L);
//        account.setAccountType("Ahorros");
//        account.setBalance(500.0);
//        account.setMovements(List.of(movement));
//
//        Customer customer = new Customer();
//        customer.setIdentification("1234567890");
//        customer.setName("John Doe");
//        customer.setGender("M");
//        customer.setAge(30);
//        customer.setPhone("0999999999");
//        customer.setAddress("Av Siempre Viva 123");
//        customer.setStatus(true);
//        customer.setAccounts(List.of(account));
//
//        // Act
//        CustomerServiceImpl.CustomerResponseDTO dto = customerService.mapToResponseDTO(customer);
//
//        // Assert
//        assertNotNull(dto);
//        assertEquals("1234567890", dto.getIdentification());
//        assertEquals("John Doe", dto.getName());
//        assertEquals("M", dto.getGender());
//        assertEquals(30, dto.getAge());
//        assertEquals("0999999999", dto.getPhone());
//        assertEquals("Av Siempre Viva 123", dto.getAddress());
//        assertTrue(dto.getStatus());
//
//        // Verificar cuentas
//        assertNotNull(dto.getAccounts());
//        assertEquals(1, dto.getAccounts().size());
//        assertEquals(12345L, dto.getAccounts().get(0).getAccountNumber());
//        assertEquals("Ahorros", dto.getAccounts().get(0).getAccountType());
//        assertEquals(500.0, dto.getAccounts().get(0).getBalance());
//
//        // Verificar movimientos
//        assertNotNull(dto.getAccounts().get(0).getMovements());
//        assertEquals(1, dto.getAccounts().get(0).getMovements().size());
//        assertEquals(1L, dto.getAccounts().get(0).getMovements().get(0).getId());
//        assertEquals(100.0, dto.getAccounts().get(0).getMovements().get(0).getAmount());
//        assertEquals(500.0, dto.getAccounts().get(0).getMovements().get(0).getBalance());
//        assertEquals("CREDITO", dto.getAccounts().get(0).getMovements().get(0).getMovementType());
//    }
//
//    @Test
//    void shouldMapCustomerWithMovementsFilteredByDateSuccessfully() {
//        // Arrange
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
//
//        Movements movementInsideRange = new Movements();
//        movementInsideRange.setId(1L);
//        movementInsideRange.setDate(LocalDate.of(2024, 6, 1));
//        movementInsideRange.setAmount(100.0);
//        movementInsideRange.setMovementType("CREDITO");
//        movementInsideRange.setBalance(600.0);
//
//        Movements movementOutsideRange = new Movements();
//        movementOutsideRange.setId(2L);
//        movementOutsideRange.setDate(LocalDate.of(2023, 12, 31));
//        movementOutsideRange.setAmount(200.0);
//        movementOutsideRange.setMovementType("DEBITO");
//        movementOutsideRange.setBalance(400.0);
//
//        Account account = new Account();
//        account.setAccountNumber(12345L);
//        account.setAccountType("Ahorros");
//        account.setBalance(500.0);
//        account.setMovements(List.of(movementInsideRange, movementOutsideRange));
//
//        Customer customer = new Customer();
//        customer.setIdentification("1234567890");
//        customer.setName("John Doe");
//        customer.setStatus(true);
//        customer.setAccounts(List.of(account));
//
//        // Act
//        CustomerMapper.CustomerResponseMapper dto = customerService.mapToResponseDTO(customer, startDate, endDate);
//
//        // Assert
//        assertNotNull(dto);
//        assertEquals("1234567890", dto.getIdentification());
//        assertEquals("John Doe", dto.getName());
//        assertTrue(dto.getStatus());
//
//        assertNotNull(dto.getAccounts());
//        assertEquals(1, dto.getAccounts().size());
//
//        CustomerServiceImpl.AccountDTO accountDTO = dto.getAccounts().get(0);
//        assertEquals(12345L, accountDTO.getAccountNumber());
//        assertEquals("Ahorros", accountDTO.getAccountType());
//        assertEquals(500.0, accountDTO.getBalance());
//
//        // Verificar que solo 1 movimiento fue mapeado (el que estaba en rango)
//        assertNotNull(accountDTO.getMovements());
//        assertEquals(1, accountDTO.getMovements().size());
//        CustomerServiceImpl.MovementsDTO movementDTO = accountDTO.getMovements().get(0);
//        assertEquals(1L, movementDTO.getId());
//        assertEquals(LocalDate.of(2024, 6, 1), movementDTO.getDate());
//        assertEquals(100.0, movementDTO.getAmount());
//        assertEquals("CREDITO", movementDTO.getMovementType());
//    }
//
//}
