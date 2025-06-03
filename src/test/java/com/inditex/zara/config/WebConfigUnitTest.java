package com.inditex.zara.config;

import com.inditex.zara.infrastructure.configs.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebConfigUnitTest {

    private WebConfig.StringToLocalDateTimeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new WebConfig.StringToLocalDateTimeConverter();
    }

    @Test
    void testConvertStandardFormat() {
        // Given
        String dateString = "2020-06-14 15:00:00";
        LocalDateTime expected = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        // When
        LocalDateTime result = converter.convert(dateString);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void testConvertAlternativeFormat() {
        // Given
        String dateString = "2020-06-14-15.00.00";
        LocalDateTime expected = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        // When
        LocalDateTime result = converter.convert(dateString);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void testConvertISOFormat() {
        // Given
        String dateString = "2020-06-14T15:00:00";
        LocalDateTime expected = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        // When
        LocalDateTime result = converter.convert(dateString);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void testConvertInvalidFormat() {
        // Given
        String dateString = "invalid-date-format";

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(dateString)
        );
        
        assertTrue(exception.getMessage().contains("Formato de fecha inválido"));
        assertTrue(exception.getMessage().contains("Use alguno de estos formatos"));
    }

    @Test
    void testConvertEmptyString() {
        // Given
        String dateString = "";

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(dateString)
        );
        
        assertTrue(exception.getMessage().contains("Formato de fecha inválido"));
    }

    @Test
    void testConvertNullString() {
        // When & Then
        assertThrows(
                NullPointerException.class,
                () -> converter.convert(null)
        );
    }

    @Test
    void testConvertUnsupportedFormat() {
        // Given
        String dateString = "2020/06/14 15:00:00";

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(dateString)
        );
        
        assertTrue(exception.getMessage().contains("Formato de fecha inválido"));
    }
} 