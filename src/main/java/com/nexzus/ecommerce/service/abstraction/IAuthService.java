package com.nexzus.ecommerce.service.abstraction;

import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.dto.request.LoginRequest;
import com.nexzus.ecommerce.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token);
    AuthResponse refreshToken(String requestRefreshToken);
}
