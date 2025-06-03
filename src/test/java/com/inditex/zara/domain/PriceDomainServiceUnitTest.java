package com.inditex.zara.domain;

import com.inditex.zara.domain.exceptions.PriceNotFoundException;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.domain.services.PriceDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceDomainServiceUnitTest {

    private PriceDomainService priceDomainService;

    @BeforeEach
    void setUp() {
        priceDomainService = new PriceDomainService();
    }

    @Test
    void selectHighestPriorityPrice_ShouldReturnHighestPriorityPrice() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price lowPriorityPrice = new Price(1, now, now, 1, 1L, 0, new BigDecimal("10.00"), "EUR");
        Price mediumPriorityPrice = new Price(1, now, now, 2, 1L, 1, new BigDecimal("15.00"), "EUR");
        Price highPriorityPrice = new Price(1, now, now, 3, 1L, 2, new BigDecimal("20.00"), "EUR");
        
        List<Price> prices = Arrays.asList(lowPriorityPrice, highPriorityPrice, mediumPriorityPrice);

        // When
        Price result = priceDomainService.selectHighestPriorityPrice(prices);

        // Then
        assertEquals(highPriorityPrice, result);
        assertEquals(2, result.priority());
    }

    @Test
    void selectHighestPriorityPrice_WithEmptyList_ShouldThrowException() {
        // Given
        List<Price> emptyPrices = Collections.emptyList();

        // When & Then
        PriceNotFoundException exception = assertThrows(
                PriceNotFoundException.class,
                () -> priceDomainService.selectHighestPriorityPrice(emptyPrices)
        );
        
        assertEquals("No prices available for selection", exception.getMessage());
    }

    @Test
    void selectHighestPriorityPrice_WithNullList_ShouldThrowException() {
        // When & Then
        PriceNotFoundException exception = assertThrows(
                PriceNotFoundException.class,
                () -> priceDomainService.selectHighestPriorityPrice(null)
        );
        
        assertEquals("No prices available for selection", exception.getMessage());
    }

    @Test
    void selectHighestPriorityPrice_WithSinglePrice_ShouldReturnThatPrice() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price singlePrice = new Price(1, now, now, 1, 1L, 5, new BigDecimal("25.99"), "EUR");
        List<Price> prices = Collections.singletonList(singlePrice);

        // When
        Price result = priceDomainService.selectHighestPriorityPrice(prices);

        // Then
        assertEquals(singlePrice, result);
    }

    @Test
    void isValidPrice_WithValidPrice_ShouldReturnTrue() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price validPrice = new Price(1, now, now, 1, 1L, 0, new BigDecimal("10.00"), "EUR");

        // When
        boolean result = priceDomainService.isValidPrice(validPrice);

        // Then
        assertTrue(result);
    }

    @Test
    void isValidPrice_WithNullPrice_ShouldReturnFalse() {
        // When
        boolean result = priceDomainService.isValidPrice(null);

        // Then
        assertFalse(result);
    }

    @Test
    void isValidPrice_WithNullPriceAmount_ShouldReturnFalse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price invalidPrice = new Price(1, now, now, 1, 1L, 0, null, "EUR");

        // When
        boolean result = priceDomainService.isValidPrice(invalidPrice);

        // Then
        assertFalse(result);
    }

    @Test
    void isValidPrice_WithZeroPriceAmount_ShouldReturnFalse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price invalidPrice = new Price(1, now, now, 1, 1L, 0, BigDecimal.ZERO, "EUR");

        // When
        boolean result = priceDomainService.isValidPrice(invalidPrice);

        // Then
        assertFalse(result);
    }

    @Test
    void isValidPrice_WithNegativePriceAmount_ShouldReturnFalse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price invalidPrice = new Price(1, now, now, 1, 1L, 0, new BigDecimal("-10.00"), "EUR");

        // When
        boolean result = priceDomainService.isValidPrice(invalidPrice);

        // Then
        assertFalse(result);
    }

    @Test
    void isValidPrice_WithNullCurrency_ShouldReturnFalse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price invalidPrice = new Price(1, now, now, 1, 1L, 0, new BigDecimal("10.00"), null);

        // When
        boolean result = priceDomainService.isValidPrice(invalidPrice);

        // Then
        assertFalse(result);
    }

    @Test
    void isValidPrice_WithEmptyCurrency_ShouldReturnFalse() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price invalidPrice = new Price(1, now, now, 1, 1L, 0, new BigDecimal("10.00"), "   ");

        // When
        boolean result = priceDomainService.isValidPrice(invalidPrice);

        // Then
        assertFalse(result);
    }
} 