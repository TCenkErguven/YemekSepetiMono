package com.yemeksepeti.mapper;

import com.yemeksepeti.dto.request.RegisterRequestDto;
import com.yemeksepeti.dto.response.RegisterResponseDto;
import com.yemeksepeti.repository.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICustomerMapper {
    ICustomerMapper INSTANCE = Mappers.getMapper(ICustomerMapper.class);

    Customer fromRegisterRequestDtoToCustomer(final RegisterRequestDto dto);
    RegisterResponseDto fromCustomerToRegisterResponseDto(final Customer customer);

}
