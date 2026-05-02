package com.nexzus.ecommerce.service.implementation;

import com.nexzus.ecommerce.config.Mapper;
import com.nexzus.ecommerce.dto.request.LoginRequest;
import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.dto.response.AuthResponse;
import com.nexzus.ecommerce.exception.DuplicateResourceException;
import com.nexzus.ecommerce.exception.ResourceNotFoundException;
import com.nexzus.ecommerce.model.RefreshToken;
import com.nexzus.ecommerce.model.User;
import com.nexzus.ecommerce.repository.RefreshTokenRepository;
import com.nexzus.ecommerce.repository.UserRepository;
import com.nexzus.ecommerce.security.jwt.JwtUtils;
import com.nexzus.ecommerce.service.abstraction.IAuthService;
import com.nexzus.ecommerce.service.abstraction.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.email())) {
            throw new DuplicateResourceException("Usuario", "correo", request.email());
        }
        User userCreated = mapper.toUserEntity(request);
        userCreated.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(userCreated);

        return generateTokenForUser(userCreated);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario",  "correo", request.email()));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return generateTokenForUser(user);
    }

    @Override
    public void logout(String token) {
        if (token != null) {
            refreshTokenService.revokeToken(token);
        }
    }

    public AuthResponse refreshToken(String requestRefreshToken) {
        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Refresh Token Rotation: Revocamos el actual y emitimos uno nuevo
                    refreshTokenService.revokeToken(requestRefreshToken);
                    return generateTokenForUser(user);
                })
                .orElseThrow(() -> new BadCredentialsException("Refresh token no encontrado o inválido"));
    }


    private AuthResponse generateTokenForUser(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateToken(userDetails);

        RefreshToken rf = refreshTokenService.create(user.getId());

        return new AuthResponse(accessToken, rf.getToken(), jwtService.getAccessTokenExpiration(), mapper.toUserDto(user));
    }
}
