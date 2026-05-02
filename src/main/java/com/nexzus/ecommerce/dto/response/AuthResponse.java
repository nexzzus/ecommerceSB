package com.nexzus.ecommerce.dto.response;

import com.nexzus.ecommerce.dto.UserDto;

public record AuthResponse(
        String accessToken,
        String regreshToken,
        String tokenType,
        Long expiresIn,
        UserDto user
) {
    public AuthResponse(String accessToken,String regreshToken, Long expiresIn, UserDto user){
        this(accessToken, regreshToken, "Bearer", expiresIn, user);
    }
}
