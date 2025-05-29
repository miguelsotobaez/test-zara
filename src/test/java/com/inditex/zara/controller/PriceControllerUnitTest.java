package com.inditex.zara.controller;

import com.inditex.zara.application.services.PriceService;
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
    private PriceService priceService;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceController priceController;

    @Test
    void getFinalPriceReturnsPriceResponse() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Integer brandId = 1;
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        Price price = new Price(1, date, endDate, brandId, productId, 0, new BigDecimal("25.45"), "EUR");

        PriceResponse expectedResponse = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(2)
                .startDate(date)
                .endDate(endDate)
                .finalPrice("25.45 EUR")
                .build();

        when(priceService.getFinalPrice(date, productId, brandId)).thenReturn(price);
        when(priceMapper.toDto(price)).thenReturn(expectedResponse);

        ResponseEntity<PriceResponse> result = priceController.getFinalPrice(date, productId, brandId);

        assertEquals(expectedResponse, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(priceService).getFinalPrice(date, productId, brandId);
        verify(priceMapper).toDto(price);
    }

    @Test
    void getFinalPriceWithConventionReturnsPriceResponse() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Integer brandId = 1;
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        Price price = new Price(1, date, endDate, brandId, productId, 0, new BigDecimal("25.45"), "EUR");

        PriceResponse expectedResponse = PriceResponse.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(2)
                .startDate(date)
                .endDate(endDate)
                .finalPrice("25.45 EUR")
                .build();

        when(priceService.getFinalPriceWithConvention(date, productId, brandId)).thenReturn(price);
        when(priceMapper.toDto(price)).thenReturn(expectedResponse);

        ResponseEntity<PriceResponse> result = priceController.getFinalPriceWithConvention(date, productId, brandId);

        assertEquals(expectedResponse, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(priceService).getFinalPriceWithConvention(date, productId, brandId);
        verify(priceMapper).toDto(price);
    }

    @Test
    void getPricesListAllReturnsPriceResponse() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Integer brandId = 1;
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        List<Price> price = PriceFixtures.getPricesFixtures();

        List<PriceResponse> expectedResponse = List.of(
                PriceResponse.builder()
                        .productId(productId)
                        .brandId(brandId)
                        .priceList(2)
                        .startDate(date)
                        .endDate(endDate)
                        .finalPrice("25.45 EUR")
                        .build()
        );

        when(priceService.getPricesListAll()).thenReturn(price);
        when(priceMapper.toDto(price)).thenReturn(expectedResponse);

        ResponseEntity<List<PriceResponse>> result = priceController.getPricesListAll();

        assertEquals(expectedResponse, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(priceService).getPricesListAll();
        verify(priceMapper).toDto(price);
    }

}
