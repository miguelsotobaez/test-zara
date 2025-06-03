package com.inditex.zara.domain.ports.in;

import com.inditex.zara.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Puerto de entrada para casos de uso relacionados con precios.
 * Define los contratos de negocio desde la perspectiva del dominio.
 */
public interface PriceInputPort {

    /**
     * Obtiene el precio final aplicable para un producto en una fecha específica
     * @param date Fecha de consulta
     * @param productId ID del producto
     * @param brandId ID de la marca
     * @return Precio final aplicable
     */
    Price getFinalPrice(LocalDateTime date, Long productId, Integer brandId);

    /**
     * Obtiene el precio final usando convención de negocio (por prioridad)
     * @param date Fecha de consulta
     * @param productId ID del producto
     * @param brandId ID de la marca
     * @return Precio final aplicable
     */
    Price getFinalPriceWithConvention(LocalDateTime date, Long productId, Integer brandId);

    /**
     * Obtiene todos los precios disponibles
     * @return Lista completa de precios
     */
    List<Price> getAllPrices();
} 