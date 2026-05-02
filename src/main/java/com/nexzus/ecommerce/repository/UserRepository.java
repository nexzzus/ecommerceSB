package com.nexzus.ecommerce.repository;

import com.nexzus.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByEmail(String email);


    Optional<User> findByEmail(String email);
}
