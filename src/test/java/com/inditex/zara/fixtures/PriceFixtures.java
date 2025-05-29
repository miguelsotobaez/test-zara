package com.inditex.zara.fixtures;

import com.inditex.zara.domain.model.Price;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class PriceFixtures {

    public List<Price> getPricesFixtures() {
        return List.of(
                Price.builder()
                        .brandId(1)
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                        .priceList(2)
                        .productId(35455L)
                        .priority(0)
                        .price(new BigDecimal("19.99"))
                        .currency("EUR")
                        .build(),
                Price.builder()
                        .brandId(2)
                        .startDate(LocalDateTime.of(2021, 1, 1, 0, 0))
                        .endDate(LocalDateTime.of(2021, 12, 31, 23, 59))
                        .priceList(3)
                        .productId(12345L)
                        .priority(1)
                        .price(new BigDecimal("29.99"))
                        .currency("EUR")
                        .build()
        );
    }
}
