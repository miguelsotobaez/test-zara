package com.inditex.zara.infrastructure.adapters.input.api;

import com.inditex.zara.infrastructure.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

public interface PriceControllerApi {

    @Operation(
            summary = "Obtener precio aplicable",
            description = "Devuelve el precio activo para un producto en una fecha específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Precio encontrado",
                            content = @Content(schema = @Schema(implementation = PriceResponse.class))
                    )})
    @GetMapping("/final-price")
    ResponseEntity<PriceResponse> getFinalPrice(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
            @RequestParam @NotNull @Positive Long productId,
            @RequestParam @NotNull @Positive Integer brandId);


    @Operation(
            summary = "Obtener precio aplicable",
            description = "Devuelve el precio activo para un producto en una fecha específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Precio encontrado",
                            content = @Content(schema = @Schema(implementation = PriceResponse.class))
                    )})
    @GetMapping("/final-price-convention")
    ResponseEntity<PriceResponse> getFinalPriceWithConvention(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
            @RequestParam @NotNull @Positive Long productId,
            @RequestParam @NotNull @Positive Integer brandId);
}
