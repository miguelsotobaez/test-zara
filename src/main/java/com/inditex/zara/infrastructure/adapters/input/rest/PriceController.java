package com.inditex.zara.infrastructure.adapters.input.rest;

import com.inditex.zara.infrastructure.adapters.input.api.PriceControllerApi;
import com.inditex.zara.infrastructure.dto.PriceResponse;
import com.inditex.zara.infrastructure.mappers.PriceMapper;
import com.inditex.zara.domain.ports.in.PriceInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST que actúa como adaptador de entrada.
 * Implementa la API y delega en el puerto de entrada del dominio.
 */
@RestController
@RequestMapping("/api/v1/rest/prices")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Prices", description = "API para gestión de precios")
public class PriceController implements PriceControllerApi {

    private final PriceInputPort priceInputPort;
    private final PriceMapper priceMapper;

    @Override
    public ResponseEntity<PriceResponse> getFinalPrice(LocalDateTime date, Long productId, Integer brandId) {
        log.info("Requesting final price for productId: {}, brandId: {}, date: {}", productId, brandId, date);
        
        return ResponseEntity.ok(
                priceMapper.toDto(priceInputPort.getFinalPrice(date, productId, brandId))
        );
    }

    @Override
    public ResponseEntity<PriceResponse> getFinalPriceWithConvention(LocalDateTime date, Long productId, Integer brandId) {
        log.info("Requesting final price with convention for productId: {}, brandId: {}, date: {}", productId, brandId, date);
        
        return ResponseEntity.ok(
                priceMapper.toDto(priceInputPort.getFinalPriceWithConvention(date, productId, brandId))
        );
    }

    @Override
    public ResponseEntity<List<PriceResponse>> getPricesListAll() {
        log.info("Requesting all prices list");
        
        return ResponseEntity.ok(
                priceMapper.toDto(priceInputPort.getAllPrices())
        );
    }
}
