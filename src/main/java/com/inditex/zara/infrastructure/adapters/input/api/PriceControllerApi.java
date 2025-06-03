package com.inditex.zara.infrastructure.adapters.input.api;

import com.inditex.zara.infrastructure.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

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
            @Parameter(description = "Fecha de consulta. Formatos aceptados: 'yyyy-MM-dd HH:mm:ss' o 'yyyy-MM-dd-HH.mm.ss'", 
                      example = "2020-06-14 15:00:00")
            @RequestParam LocalDateTime date,
            @RequestParam @NotNull @Positive Long productId,
            @RequestParam @NotNull @Positive Integer brandId);


    @Operation(
            summary = "Obtener precio aplicable con convención de negocio",
            description = "Devuelve el precio activo para un producto en una fecha específica usando lógica de prioridad",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Precio encontrado",
                            content = @Content(schema = @Schema(implementation = PriceResponse.class))
                    )})
    @GetMapping("/final-price-convention")
    ResponseEntity<PriceResponse> getFinalPriceWithConvention(
            @Parameter(description = "Fecha de consulta. Formatos aceptados: 'yyyy-MM-dd HH:mm:ss' o 'yyyy-MM-dd-HH.mm.ss'", 
                      example = "2020-06-14 15:00:00")
            @RequestParam LocalDateTime date,
            @RequestParam @NotNull @Positive Long productId,
            @RequestParam @NotNull @Positive Integer brandId);

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
    ResponseEntity<List<PriceResponse>> getPricesListAll();
}
