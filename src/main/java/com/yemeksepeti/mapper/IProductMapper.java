package com.yemeksepeti.mapper;

import com.yemeksepeti.dto.request.SaveProductRequestDto;
import com.yemeksepeti.dto.response.SaveProductResponseDto;
import com.yemeksepeti.repository.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProductMapper {
    IProductMapper INSTANCE = Mappers.getMapper(IProductMapper.class);

    Product fromSaveProductRequestDtoToProduct(final SaveProductRequestDto dto);
    SaveProductResponseDto fromProductToSaveProductResponseDto(final Product product);

}
