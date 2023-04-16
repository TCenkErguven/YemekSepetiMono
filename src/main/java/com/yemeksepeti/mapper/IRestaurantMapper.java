package com.yemeksepeti.mapper;

import com.yemeksepeti.dto.request.SaveRestaurantRequestDto;
import com.yemeksepeti.dto.request.UpdateRestaurantRequestDto;
import com.yemeksepeti.dto.response.RegisterResponseDto;
import com.yemeksepeti.dto.response.SaveRestaurantResponseDto;
import com.yemeksepeti.dto.response.UpdateRestaurantResponseDto;
import com.yemeksepeti.repository.entity.Restaurant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRestaurantMapper {
    IRestaurantMapper INSTANCE = Mappers.getMapper(IRestaurantMapper.class);

    Restaurant fromSaveRestaurantRequestDtoToRestaurant(final SaveRestaurantRequestDto dto);
    SaveRestaurantResponseDto fromRestaurantToSaveRestaurantResponseDto(final Restaurant restaurant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Restaurant fromUpdateRestaurantDtoToRestaurant(UpdateRestaurantRequestDto dto, @MappingTarget Restaurant restaurant);

    UpdateRestaurantResponseDto fromRestaurantToUpdateResponseDto(final Restaurant restaurant);
}
