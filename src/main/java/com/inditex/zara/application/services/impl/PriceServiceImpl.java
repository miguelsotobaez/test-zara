package com.inditex.zara.application.services.impl;

import com.inditex.zara.application.services.PriceService;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.infrastructure.exceptions.PriceNotFoundException;
import com.inditex.zara.domain.ports.out.PriceOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceOutputPort priceOutputPort; // Usa el puerto

    @Override
    public Price getFinalPrice(LocalDateTime date, Long productId, Integer brandId) {
        return priceOutputPort.findFinalPrice(brandId, productId, date)
                .orElseThrow(() -> new PriceNotFoundException("Price not found"));
    }

    @Override
    public Price getFinalPriceWithConvention(LocalDateTime date, Long productId, Integer brandId) {
        return priceOutputPort.findFinalPriceWithConvention(brandId, productId, date)
                .stream()
                .max(Comparator.comparingInt(Price::priority))
                .orElseThrow(() -> new PriceNotFoundException("Price not found"));
    }

    @Override
    public List<Price> getPricesListAll() {
        return priceOutputPort.findAll();
    }
}
