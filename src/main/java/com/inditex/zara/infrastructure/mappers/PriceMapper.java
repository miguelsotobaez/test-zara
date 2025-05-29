package com.inditex.zara.infrastructure.mappers;

import com.inditex.zara.infrastructure.dto.PriceResponse;
import com.inditex.zara.domain.model.Price;
import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PriceMapper {

    @Mapping(target = "currency", source = "curr")
    Price toDomain(PriceEntity entity);

    @Mapping(target = "finalPrice", expression = "java(price.price().toString() + \" \" + price.currency())")
    PriceResponse toDto(Price price);

    List<PriceResponse> toDto(List<Price> prices);
}
