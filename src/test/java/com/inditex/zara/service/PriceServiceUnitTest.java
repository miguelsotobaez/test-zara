package com.inditex.zara.service;

import com.inditex.zara.application.services.impl.PriceServiceImpl;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.domain.ports.out.PriceOutputPort;
import com.inditex.zara.infrastructure.exceptions.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceUnitTest {

    @Mock
    private PriceOutputPort priceOutputPort;

    @InjectMocks
    private PriceServiceImpl priceService;


    @Test
    void whenValidRequestThenReturnPrice() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
        Price price = new Price(1, startDate, endDate, 2, 35455L, 0, new BigDecimal("25.45"), "EUR");

        when(priceOutputPort.findFinalPrice(any(), any(), any())).thenReturn(Optional.of(price));

        Price response = priceService.getFinalPrice(startDate, 35455L, 1);

        assertThat(response.price()).isEqualTo("25.45");
        assertThat(response.productId()).isEqualTo(35455L);
        verify(priceOutputPort, times(1)).findFinalPrice(any(), any(), any());
    }

    @Test
    void whenPriceNotFoundThenThrowException() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        when(priceOutputPort.findFinalPrice(any(), any(), any())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getFinalPrice(startDate, 35455L, 1)
        );
        verify(priceOutputPort, times(1)).findFinalPrice(any(), any(), any());
    }


    @Test
    void whenValidRequestThenReturnPriceWithConvention() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
        List<Price> price = Collections.singletonList(new Price(1, startDate, endDate, 2, 35455L, 0, new BigDecimal("25.45"), "EUR"));

        when(priceOutputPort.findFinalPriceWithConvention(any(), any(), any())).thenReturn(price);

        Price response = priceService.getFinalPriceWithConvention(startDate, 35455L, 1);

        assertThat(response.price()).isEqualTo("25.45");
        assertThat(response.productId()).isEqualTo(35455L);
        verify(priceOutputPort, times(1)).findFinalPriceWithConvention(any(), any(), any());
    }

    @Test
    void whenPriceWithConventionNotFoundThenThrowException() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        when(priceOutputPort.findFinalPriceWithConvention(any(), any(), any())).thenReturn(Collections.emptyList());

        org.junit.jupiter.api.Assertions.assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getFinalPriceWithConvention(startDate, 35455L, 1)
        );
        verify(priceOutputPort, times(1)).findFinalPriceWithConvention(any(), any(), any());
    }

    @Test
    void whenValidRequestThenReturnPriceList() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
        List<Price> price = Collections.singletonList(new Price(1, startDate, endDate, 2, 35455L, 0, new BigDecimal("25.45"), "EUR"));

        when(priceOutputPort.findAll()).thenReturn(price);

        List<Price> response = priceService.getPricesListAll();

        assertThat(response.getFirst().price()).isEqualTo("25.45");
        assertThat(response.getFirst().productId()).isEqualTo(35455L);
        verify(priceOutputPort, times(1)).findAll();
    }
}
