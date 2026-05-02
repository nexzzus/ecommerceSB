package com.nexzus.ecommerce.config;

import com.nexzus.ecommerce.dto.UserDto;
import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.model.User;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    User toUserEntity(RegisterRequest request);

    UserDto toUserDto(User user);
}
