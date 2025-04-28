package com.prueba.tecnica.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.tecnica.banking.domain.CustomerMapper;
import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.CustomerService;
import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFindCustomersSuccessfully() throws Exception {
        //
        CustomerServiceImpl.CustomerResponseDTO customer1 = new CustomerServiceImpl.CustomerResponseDTO();
        customer1.setIdentification("1234567890");
        customer1.setName("John Doe");
        customer1.setStatus(true);

        CustomerServiceImpl.CustomerResponseDTO customer2 = new CustomerServiceImpl.CustomerResponseDTO();
        customer2.setIdentification("0987654321");
        customer2.setName("Jane Smith");
        customer2.setStatus(false);

        //when
        List<CustomerServiceImpl.CustomerResponseDTO> customers = List.of(customer1, customer2);
        when(customerService.findCustomers(any(CommonHeaders.class))).thenReturn(customers);

        //then
        mockMvc.perform(get("/v1/customers")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].identification").value("1234567890"))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].identification").value("0987654321"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].status").value(false));
    }

    @Test
    void shouldRegisterCustomerSuccessfully() throws Exception {
        //
        Customer mockCustomer = new Customer();
        mockCustomer.setIdentification("1234567890");
        mockCustomer.setName("John Doe");
        mockCustomer.setGender("M");
        mockCustomer.setAge(28);
        mockCustomer.setAddress("UIO");
        mockCustomer.setPassword("123");
        mockCustomer.setPhone("0982459802");
        mockCustomer.setStatus(true);

        //when
        when(customerService.saveCustomer(any(Customer.class), any(CommonHeaders.class)))
                .thenReturn(mockCustomer);
        String requestBody = objectMapper.writeValueAsString(mockCustomer);

        //then
        mockMvc.perform(post("/v1/add-customer")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identification").value("1234567890"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setIdentification("0987654321");
        updatedCustomer.setIdentification("1234567890");
        updatedCustomer.setName("John Doe");
        updatedCustomer.setGender("M");
        updatedCustomer.setAge(28);
        updatedCustomer.setAddress("UIO");
        updatedCustomer.setPassword("123");
        updatedCustomer.setPhone("0982459802");
        updatedCustomer.setStatus(true);

        when(customerService.updateCustomer(any(Customer.class), any(CommonHeaders.class)))
                .thenReturn(updatedCustomer);

        String requestBody = objectMapper.writeValueAsString(updatedCustomer);

        mockMvc.perform(post("/v1/update-customer")
                        .header("x-device", "web")
                        .header("x-device-ip", "192.168.1.100")
                        .header("x-session", "abc456")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identification").value("1234567890"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void shouldFindCustomerSuccessfully() throws Exception {
        Customer requestCustomer = new Customer();
        requestCustomer.setIdentification("1234567890");

        CustomerServiceImpl.CustomerResponseDTO responseDTO = new CustomerServiceImpl.CustomerResponseDTO();
        responseDTO.setIdentification("1234567890");
        responseDTO.setName("John Doe");
        responseDTO.setStatus(true);

        //when
        when(customerService.findCustomerWithAccountsAndMovements(any(Customer.class), any(CommonHeaders.class)))
                .thenReturn(responseDTO);

        String requestBody = objectMapper.writeValueAsString(requestCustomer);

        //them
        mockMvc.perform(post("/v1/find-customer")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identification").value("1234567890"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void shouldFindCustomerByDateSuccessfully() throws Exception {
        // Arrange
        CustomerMapper.CustomerResponseMapper responseMapper = new CustomerMapper.CustomerResponseMapper();
        responseMapper.setIdentification("1234567890");
        responseMapper.setName("John Doe");
        responseMapper.setStatus(true);

        //when
        when(customerService.findCustomerWithMovementsBetweenDates(
                any(String.class),
                any(LocalDate.class),
                any(LocalDate.class),
                any(CommonHeaders.class))
        ).thenReturn(responseMapper);

        //then
        mockMvc.perform(get("/v1/find-customer-by-date")
                        .param("id", "1234567890")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identification").value("1234567890"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        String customerId = "1234567890";

        mockMvc.perform(delete("/v1/delete-customer")
                        .param("id", customerId)
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204 No Content
    }


}
