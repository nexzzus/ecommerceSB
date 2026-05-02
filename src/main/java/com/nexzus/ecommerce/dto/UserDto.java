package com.nexzus.ecommerce.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.nexzus.ecommerce.model.User}
 */
public record UserDto(String firstName, String lastName, String email, String phone,
                      String address) implements Serializable {
}