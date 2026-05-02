package com.nexzus.ecommerce.service.abstraction;

import com.nexzus.ecommerce.model.RefreshToken;

import java.util.UUID;

public interface IRefreshTokenService {
    RefreshToken create(UUID userId);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    void revokeToken(String tokenString);
    void revokedAllUserTokens(UUID userId);
}
