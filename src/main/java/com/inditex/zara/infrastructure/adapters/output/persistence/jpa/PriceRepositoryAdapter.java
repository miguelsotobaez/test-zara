package com.inditex.zara.infrastructure.adapters.output.persistence.jpa;

import com.inditex.zara.infrastructure.mappers.PriceMapper;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.repository.PriceJpaRepository;
import com.inditex.zara.domain.ports.out.PriceOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa el puerto de salida para persistencia de precios.
 * Convierte entre objetos de dominio y entidades JPA.
 */
@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceOutputPort {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findFinalPrice(
            Integer brandId,
            Long productId,
            LocalDateTime date
    ) {
        return priceJpaRepository
                .findApplicablePrice(brandId, productId, date)
                .map(priceMapper::toDomain);
    }

    @Override
    public List<Price> findFinalPriceWithConvention(
            Integer brandId,
            Long productId,
            LocalDateTime date
    ) {
        return priceJpaRepository
                .findAllApplicablePrices(brandId, productId, date)
                .stream()
                .map(priceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Price> findAll() {
        return priceJpaRepository.findAll()
                .stream()
                .map(priceMapper::toDomain)
                .toList();
    }
}
