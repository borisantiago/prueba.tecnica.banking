package com.prueba.tecnica.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.tecnica.banking.domain.entity.Account;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterAccountSuccessfully() throws Exception {
        Account mockAccount = new Account();
        mockAccount.setAccountNumber(1L);
        mockAccount.setBalance(1000.0);
        mockAccount.setAccountType("Ahorro");
        mockAccount.setAccountStatus(true);

        when(accountService.saveAccount(any(Account.class), any(CommonHeaders.class)))
                .thenReturn(mockAccount);

        String requestBody = objectMapper.writeValueAsString(mockAccount);

        mockMvc.perform(post("/v1/add-account")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.balance").value(1000.0))
                .andExpect(jsonPath("$.accountType").value("Ahorro"));
    }

    @Test
    void shouldUpdateAccountSuccessfully() throws Exception {
        // Arrange
        Account updatedAccount = new Account();
        updatedAccount.setAccountNumber(2L);
        updatedAccount.setBalance(1500.0);
        updatedAccount.setAccountType("Corriente");
        updatedAccount.setAccountStatus(true);

        when(accountService.updateAccount(any(Account.class), any(CommonHeaders.class)))
                .thenReturn(updatedAccount);

        String requestBody = objectMapper.writeValueAsString(updatedAccount);

        // Act & Assert
        mockMvc.perform(post("/v1/update-account")
                        .header("x-device", "web") // ✅ debe cumplir con patrón: ^[a-zA-Z0-9_-]+$
                        .header("x-device-ip", "192.168.1.100")
                        .header("x-session", "abc456")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(2L))
                .andExpect(jsonPath("$.balance").value(1500.0))
                .andExpect(jsonPath("$.accountType").value("Corriente"))
                .andExpect(jsonPath("$.accountStatus").value(true));
    }

    @Test
    void shouldDeleteAccountSuccessfully() throws Exception {
        String accountId = "123456";

        //then
        mockMvc.perform(delete("/v1/delete-account")
                        .param("id", accountId)
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204 No Content
    }

}
