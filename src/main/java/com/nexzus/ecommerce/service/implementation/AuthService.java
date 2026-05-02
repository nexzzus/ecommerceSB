package com.nexzus.ecommerce.service.implementation;

import com.nexzus.ecommerce.config.Mapper;
import com.nexzus.ecommerce.dto.UserDto;
import com.nexzus.ecommerce.dto.request.RegisterRequest;
import com.nexzus.ecommerce.dto.response.AuthResponse;
import com.nexzus.ecommerce.model.User;
import com.nexzus.ecommerce.repository.UserRepository;
import com.nexzus.ecommerce.service.abstraction.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.email())){
//            TODO: Implementar manejo de excepciones
            throw new RuntimeException("Email already exists");
        }
        User userCreated =  mapper.toUserEntity(request);
        userCreated.setPassword(passwordEncoder.encode(request.password()));
        UserDto dto = mapper.toUserDto(userCreated);

        userRepository.save(userCreated);

        return new AuthResponse("", "", null, dto);
    }
}
