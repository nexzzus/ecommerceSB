package com.nexzus.ecommerce.config;

import com.nexzus.ecommerce.dto.UserDto;
import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.model.User;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    @org.mapstruct.Mapping(target = "email", source = "email")
    @org.mapstruct.Mapping(target = "firstName", source = "firstName")
    @org.mapstruct.Mapping(target = "lastName", source = "lastName")
    @org.mapstruct.Mapping(target = "password", ignore = true)
//    @org.mapstruct.Mapping(target = "address", source = "address")
    User toUserEntity(RegisterRequest request);

    UserDto toUserDto(User user);
}
