package com.inditex.zara.controller;

import com.inditex.zara.domain.ports.in.PriceInputPort;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.fixtures.PriceFixtures;
import com.inditex.zara.infrastructure.adapters.input.rest.PriceController;
import com.inditex.zara.infrastructure.dto.PriceResponse;
import com.inditex.zara.infrastructure.mappers.PriceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerUnitTest {

    @Mock
    private PriceInputPort priceInputPort;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceController priceController;

    @Test
    void testGetFinalPrice() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        Price mockPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        PriceResponse mockResponse = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .finalPrice("25.45 EUR")
                .build();

        when(priceInputPort.getFinalPrice(date, productId, brandId)).thenReturn(mockPrice);
        when(priceMapper.toDto(mockPrice)).thenReturn(mockResponse);

        ResponseEntity<PriceResponse> response = priceController.getFinalPrice(date, productId, brandId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(priceInputPort).getFinalPrice(date, productId, brandId);
        verify(priceMapper).toDto(mockPrice);
    }

    @Test
    void testGetFinalPriceWithConvention() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Integer brandId = 1;

        Price mockPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        PriceResponse mockResponse = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .finalPrice("25.45 EUR")
                .build();

        when(priceInputPort.getFinalPriceWithConvention(date, productId, brandId)).thenReturn(mockPrice);
        when(priceMapper.toDto(mockPrice)).thenReturn(mockResponse);

        ResponseEntity<PriceResponse> response = priceController.getFinalPriceWithConvention(date, productId, brandId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(priceInputPort).getFinalPriceWithConvention(date, productId, brandId);
        verify(priceMapper).toDto(mockPrice);
    }

    @Test
    void testGetPricesListAll() {
        List<Price> mockPrices = PriceFixtures.getPricesFixtures();
        List<PriceResponse> mockResponses = List.of(
                PriceResponse.builder().finalPrice("19.99 EUR").build(),
                PriceResponse.builder().finalPrice("29.99 EUR").build()
        );

        when(priceInputPort.getAllPrices()).thenReturn(mockPrices);
        when(priceMapper.toDto(mockPrices)).thenReturn(mockResponses);

        ResponseEntity<List<PriceResponse>> response = priceController.getPricesListAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponses, response.getBody());
        verify(priceInputPort).getAllPrices();
        verify(priceMapper).toDto(mockPrices);
    }
}
