package com.inditex.zara.mapper;

import com.inditex.zara.fixtures.PriceFixtures;
import com.inditex.zara.infrastructure.dto.PriceResponse;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.entity.PriceEntity;
import com.inditex.zara.infrastructure.mappers.PriceMapper;
import com.inditex.zara.infrastructure.mappers.PriceMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceMapperUnitTest {

    private final PriceMapper priceMapper = new PriceMapperImpl();

    @Test
    void testToEntityToDomain() {
        PriceEntity entity = new PriceEntity();
        entity.setId(1L);
        entity.setBrandId(1);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        entity.setPriceList(2);
        entity.setProductId(35455L);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("25.99"));
        entity.setCurr("EUR");

        Price price = priceMapper.toDomain(entity);

        assertNotNull(price);
        assertEquals("EUR", price.currency());
        assertEquals(new BigDecimal("25.99"), price.price());
    }

    @Test
    void testToDto() {
        Price price = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(2)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("19.99"))
                .currency("EUR")
                .build();

        PriceResponse dto = priceMapper.toDto(price);

        assertNotNull(dto);
        assertEquals("19.99 EUR", dto.getFinalPrice());
    }

    @Test
    void testToDtoList() {
        List<Price> price = PriceFixtures.getPricesFixtures();

        List<PriceResponse> dto = priceMapper.toDto(price);

        assertNotNull(dto);
        assertEquals("19.99 EUR", dto.getFirst().getFinalPrice());
    }

    @Test
    void testToDtoWhenPriceIsNull() {
        PriceResponse dto = priceMapper.toDto((Price) null);
        assertNull(dto);
    }

    @Test
    void testToDomainWhenPriceIsNull() {
        Price dto = priceMapper.toDomain((PriceEntity) null);
        assertNull(dto);
    }

    @Test
    void testToDtoListWhenListPriceIsNull() {
        List<PriceResponse> dto = priceMapper.toDto((List<Price>) null);
        assertNull(dto);
    }
}


