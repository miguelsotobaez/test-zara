package com.inditex.zara.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerDateFormatIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {
        "2020-06-14 15:00:00",      // Formato estándar
        "2020-06-14-15.00.00",      // Formato alternativo
        "2020-06-14T15:00:00",      // Formato ISO
        "2020-06-14 15:00",         // Sin segundos
        "2020-06-14-15.00"          // Sin segundos, formato alternativo
    })
    @DisplayName("Debe aceptar diferentes formatos de fecha válidos")
    void shouldAcceptDifferentValidDateFormats(String dateFormat) throws Exception {
        mockMvc.perform(get("/api/v1/rest/prices/final-price")
                        .param("date", dateFormat)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.finalPrice", notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "invalid-date",
        "2020/06/14 15:00:00",      // Formato con barras
        "14-06-2020 15:00:00",      // Orden incorrecto
        "2020-06-14",               // Solo fecha
        "15:00:00"                  // Solo hora
    })
    @DisplayName("Debe rechazar formatos de fecha inválidos")
    void shouldRejectInvalidDateFormats(String dateFormat) throws Exception {
        mockMvc.perform(get("/api/v1/rest/prices/final-price")
                        .param("date", dateFormat)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid date format")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "2020-06-14 15:00:00",      // Formato estándar
        "2020-06-14-15.00.00"       // Formato alternativo
    })
    @DisplayName("Debe funcionar con convención de negocio para diferentes formatos")
    void shouldWorkWithConventionForDifferentFormats(String dateFormat) throws Exception {
        mockMvc.perform(get("/api/v1/rest/prices/final-price-convention")
                        .param("date", dateFormat)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.finalPrice", notNullValue()));
    }
} 