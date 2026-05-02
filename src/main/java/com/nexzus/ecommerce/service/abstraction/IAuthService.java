package com.nexzus.ecommerce.service.abstraction;

import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
}
