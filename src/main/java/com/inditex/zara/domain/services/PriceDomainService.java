package com.inditex.zara.domain.services;

import com.inditex.zara.domain.exceptions.PriceNotFoundException;
import com.inditex.zara.domain.model.Price;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Servicio de dominio que encapsula la lógica de negocio relacionada con precios.
 * Contiene las reglas de negocio puras sin dependencias externas.
 */
@Service
public class PriceDomainService {

    /**
     * Selecciona el precio con mayor prioridad de una lista de precios aplicables.
     * Regla de negocio: cuando hay múltiples precios aplicables, 
     * se debe seleccionar el que tenga mayor prioridad.
     * 
     * @param prices Lista de precios aplicables
     * @return Precio con mayor prioridad
     * @throws PriceNotFoundException si no hay precios disponibles
     */
    public Price selectHighestPriorityPrice(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new PriceNotFoundException("No prices available for selection");
        }

        return prices.stream()
                .max(Comparator.comparingInt(Price::priority))
                .orElseThrow(() -> new PriceNotFoundException("Unable to determine highest priority price"));
    }

    /**
     * Valida que un precio sea válido según las reglas de negocio.
     * 
     * @param price Precio a validar
     * @return true si el precio es válido
     */
    public boolean isValidPrice(Price price) {
        return price != null 
                && price.price() != null 
                && price.price().compareTo(java.math.BigDecimal.ZERO) > 0
                && price.currency() != null 
                && !price.currency().trim().isEmpty();
    }
} 