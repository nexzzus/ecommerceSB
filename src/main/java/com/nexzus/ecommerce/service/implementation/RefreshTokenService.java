package com.nexzus.ecommerce.service.implementation;

import com.nexzus.ecommerce.model.RefreshToken;
import com.nexzus.ecommerce.model.User;
import com.nexzus.ecommerce.repository.RefreshTokenRepository;
import com.nexzus.ecommerce.repository.UserRepository;
import com.nexzus.ecommerce.service.abstraction.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${api.jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationTime;

    @Override
    public RefreshToken create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .revoked(false)
                .expiresAt(Instant.now().plusMillis(refreshTokenExpirationTime))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (!refreshToken.isValid()){
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
//            TODO: EXCEPCION PERSONALIZADA
                throw new RuntimeException("Invalid refresh token");
        }
        return refreshToken;
    }

    @Override
    public void revokeToken(String tokenString) {
        refreshTokenRepository.findByToken(tokenString).ifPresent(token->{
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    @Override
    public void revokedAllUserTokens(UUID userId) {
        userRepository.findById(userId).ifPresent(refreshTokenRepository::revokeAllUserTokens);
    }
}
