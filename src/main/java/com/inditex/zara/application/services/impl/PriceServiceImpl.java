package com.inditex.zara.application.services.impl;

import com.inditex.zara.application.services.PriceService;
import com.inditex.zara.domain.exceptions.PriceNotFoundException;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.domain.ports.in.PriceInputPort;
import com.inditex.zara.domain.ports.out.PriceOutputPort;
import com.inditex.zara.domain.services.PriceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de aplicación para casos de uso de precios.
 * Orquesta las operaciones usando puertos y servicios de dominio.
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService, PriceInputPort {

    private final PriceOutputPort priceOutputPort;
    private final PriceDomainService priceDomainService;

    @Override
    public Price getFinalPrice(LocalDateTime date, Long productId, Integer brandId) {
        return priceOutputPort.findFinalPrice(brandId, productId, date)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for the given criteria"));
    }

    @Override
    public Price getFinalPriceWithConvention(LocalDateTime date, Long productId, Integer brandId) {
        List<Price> applicablePrices = priceOutputPort.findFinalPriceWithConvention(brandId, productId, date);
        
        // Usar el servicio de dominio para aplicar la lógica de negocio
        return priceDomainService.selectHighestPriorityPrice(applicablePrices);
    }

    @Override
    public List<Price> getPricesListAll() {
        return getAllPrices();
    }

    @Override
    public List<Price> getAllPrices() {
        return priceOutputPort.findAll();
    }
}
