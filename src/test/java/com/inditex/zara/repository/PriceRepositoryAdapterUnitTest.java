package com.inditex.zara.repository;

import com.inditex.zara.domain.model.Price;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.PriceRepositoryAdapter;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.entity.PriceEntity;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.repository.PriceJpaRepository;
import com.inditex.zara.infrastructure.mappers.PriceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterUnitTest {

    @Mock
    private PriceJpaRepository priceJpaRepository;

    @Mock
    private PriceMapper priceMapper;

    private PriceRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new PriceRepositoryAdapter(priceJpaRepository, priceMapper);
    }

    @Test
    void testFindFinalPriceReturnsMappedPrice() {
        Integer brandId = 1;
        Long productId = 2L;
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        Optional<PriceEntity> entity = Optional.of(new PriceEntity());
        Price price = new Price(1, startDate, endDate, 2, 35455L, 0, new BigDecimal("25.45"), "EUR");

        when(priceJpaRepository.findApplicablePrice(brandId, productId, date)).thenReturn(entity);
        when(priceMapper.toDomain(any(PriceEntity.class))).thenReturn(price);

        Optional<Price> result = adapter.findFinalPrice(brandId, productId, date);

        assertTrue(result.isPresent());
        assertEquals(price, result.get());
        verify(priceJpaRepository).findApplicablePrice(brandId, productId, date);
    }

    @Test
    void testFindFinalPriceWithConventionReturnsMappedPrice() {
        Integer brandId = 1;
        Long productId = 2L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        PriceEntity entity = new PriceEntity();
        List<PriceEntity> entities = Collections.singletonList(entity);

        Price expectedPrice = new Price(1, date, date, 2, 35455L, 0, new BigDecimal("25.45"), "EUR");

        when(priceJpaRepository.findAllApplicablePrices(brandId, productId, date)).thenReturn(entities);
        when(priceMapper.toDomain(entity)).thenReturn(expectedPrice);

        List<Price> result = adapter.findFinalPriceWithConvention(brandId, productId, date);

        assertEquals(1, result.size());
        assertEquals(expectedPrice, result.getFirst());
        verify(priceJpaRepository).findAllApplicablePrices(brandId, productId, date);
    }

    @Test
    void testFindAllReturnsMappedPrice() {
        PriceEntity entity = new PriceEntity();
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
        Price price = new Price(1, startDate, endDate, 2, 35455L, 0, new BigDecimal("25.45"), "EUR");

        when(priceJpaRepository.findAll()).thenReturn(List.of(entity));
        when(priceMapper.toDomain(entity)).thenReturn(price);

        List<Price> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals(price, result.getFirst());
        verify(priceJpaRepository).findAll();
    }


//    @Test
//    void testFindFinalPrice_ReturnsEmpty() {
//        Integer brandId = 1;
//        Long productId = 2L;
//        LocalDateTime date = LocalDateTime.now();
//
//        when(jpaRepository.findFinalPriceWithJpql(brandId, productId, date)).thenReturn(Optional.empty());
//
//        Optional<Price> result = adapter.findFinalPrice(brandId, productId, date);
//
//        assertFalse(result.isPresent());
//        verify(jpaRepository).findFinalPriceWithJpql(brandId, productId, date);
//        verify(priceMapper, never()).toDomain(any());
//    }
//
//    @Test
//    void testFindFinalPriceWithConvention_ReturnsMappedPrices() {
//        Integer brandId = 1;
//        Long productId = 2L;
//        LocalDateTime date = LocalDateTime.now();
//
//        Object entity = new Object();
//        Price price = new Price();
//
//        when(jpaRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                brandId, productId, date, date)).thenReturn(List.of(entity));
//        when(priceMapper.toDomain(entity)).thenReturn(price);
//
//        List<Price> result = adapter.findFinalPriceWithConvention(brandId, productId, date);
//
//        assertEquals(1, result.size());
//        assertEquals(price, result.get(0));
//        verify(jpaRepository).findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                brandId, productId, date, date);
//        verify(priceMapper).toDomain(entity);
//    }
//
//    @Test
//    void testFindFinalPriceWithConvention_ReturnsEmptyList() {
//        Integer brandId = 1;
//        Long productId = 2L;
//        LocalDateTime date = LocalDateTime.now();
//
//        when(jpaRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                brandId, productId, date, date)).thenReturn(Collections.emptyList());
//
//        List<Price> result = adapter.findFinalPriceWithConvention(brandId, productId, date);
//
//        assertTrue(result.isEmpty());
//        verify(jpaRepository).findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                brandId, productId, date, date);
//        verify(priceMapper, never()).toDomain(any());
//    }
//
//    @Test
//    void testFindAll_ReturnsMappedPrices() {
//        Object entity = new Object();
//        Price price = new Price();
//
//        when(jpaRepository.findAll()).thenReturn(List.of(entity));
//        when(priceMapper.toDomain(entity)).thenReturn(price);
//
//        List<Price> result = adapter.findAll();
//
//        assertEquals(1, result.size());
//        assertEquals(price, result.get(0));
//        verify(jpaRepository).findAll();
//        verify(priceMapper).toDomain(entity);
//    }
//
//    @Test
//    void testFindAll_ReturnsEmptyList() {
//        when(jpaRepository.findAll()).thenReturn(Collections.emptyList());
//
//        List<Price> result = adapter.findAll();
//
//        assertTrue(result.isEmpty());
//        verify(jpaRepository).findAll();
//        verify(priceMapper, never()).toDomain(any());
//    }
}
