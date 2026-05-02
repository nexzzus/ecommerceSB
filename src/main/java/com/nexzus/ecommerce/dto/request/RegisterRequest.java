package com.nexzus.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(min = 3, max = 50, message = "El nombre debe tener enter {min} y {max} carcteres")
        @NotBlank(message = "El nombre es obligatorio")
        String firstName,

        @Size(min = 3, max = 50, message = "El apellido debe tener entre {min} y {max} caracteres")
        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 3, message = "La contraseña debe tener al menos {min} caracteres")
        @Size(max = 200, message = "La contraseña no puede tener mas de {max} caracteres")
        String password,

        @NotBlank(message = "Debe confirmar la contraseña")
        String confirmPassword,

        @Size(max = 10, message = "El teléfono no es válido")
        String phone,

        @Size(min = 5, max = 50, message = "La dirección debe tener entre {min} y {max} caracteres")
        String address
        ) {
}
