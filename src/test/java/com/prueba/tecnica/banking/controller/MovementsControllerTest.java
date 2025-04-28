package com.prueba.tecnica.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.tecnica.banking.domain.entity.Movements;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.MovementsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = MovementsController.class)
public class MovementsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovementsService movementsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterMovementSuccessfully() throws Exception {
        //
        Movements mockMovement = new Movements();
        mockMovement.setId(1L);
        mockMovement.setMovementType("CREDITO");
        mockMovement.setAmount(100.0);
        mockMovement.setBalance(1100.0);

        //when
        when(movementsService.saveMovements(any(Movements.class), any(CommonHeaders.class)))
                .thenReturn(mockMovement);

        String requestBody = objectMapper.writeValueAsString(mockMovement);

        //then
        mockMvc.perform(post("/v1/add-movements")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.movementType").value("CREDITO"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.balance").value(1100.0));
    }

    @Test
    void shouldUpdateMovementSuccessfully() throws Exception {
        //
        Movements updatedMovement = new Movements();
        updatedMovement.setId(1L);
        updatedMovement.setMovementType("DEBITO");
        updatedMovement.setAmount(50.0);
        updatedMovement.setBalance(950.0);

        //when
        when(movementsService.updateMovements(any(Movements.class), any(CommonHeaders.class)))
                .thenReturn(updatedMovement);

        String requestBody = objectMapper.writeValueAsString(updatedMovement);

        //then
        mockMvc.perform(post("/v1/update-movements")
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.movementType").value("DEBITO"))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.balance").value(950.0));
    }

    @Test
    void shouldDeleteMovementSuccessfully() throws Exception {
        String movementId = "1";

        // No se necesita configurar when(...) ya que el método deleteMovementForId es void

        mockMvc.perform(delete("/v1/delete-movement")
                        .param("id", movementId)
                        .header("x-device", "android")
                        .header("x-device-ip", "192.168.1.1")
                        .header("x-session", "abc123")
                        .header("x-guid", "550e8777-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204 No Content
    }

}
