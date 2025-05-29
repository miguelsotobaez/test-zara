package com.inditex.zara.infrastructure.adapters.input.rest;

import com.inditex.zara.application.services.PriceService;
import com.inditex.zara.infrastructure.adapters.input.api.PriceControllerApi;
import com.inditex.zara.infrastructure.dto.PriceResponse;
import com.inditex.zara.infrastructure.mappers.PriceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rest/prices")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Prices", description = "API para gestión de precios")
public class PriceController implements PriceControllerApi {

    private final PriceService priceService;

    private final PriceMapper priceMapper;

    @Override
    public ResponseEntity<PriceResponse> getFinalPrice(LocalDateTime date, Long productId, Integer brandId) {
        log.info("Called priceService.getFinalPrice");
        return ResponseEntity.ok(priceMapper.toDto(priceService.getFinalPrice(date, productId, brandId)));
    }

    @Override
    public ResponseEntity<PriceResponse> getFinalPriceWithConvention(LocalDateTime date, Long productId, Integer brandId) {
        log.info("priceService.getFinalPriceWithConvention");
        return ResponseEntity.ok(priceMapper.toDto(priceService.getFinalPriceWithConvention(date, productId, brandId)));
    }

    @Operation(
            summary = "Obtener lista completa de precios",
            description = "Devuelve todos los precios disponibles",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de precios obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceResponse.class, type = "array")))})
    @GetMapping("/price-list-all")
    public ResponseEntity<List<PriceResponse>> getPricesListAll() {
        log.info("priceService.getPricesList");
        return ResponseEntity.ok(priceMapper.toDto(priceService.getPricesListAll()));
    }
}
