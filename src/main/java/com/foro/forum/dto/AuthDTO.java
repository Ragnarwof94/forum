package com.foro.forum.dto;

import jakarta.validation.constraints.Email; // Valida formato de email
import jakarta.validation.constraints.NotBlank; // Valida que no sea nulo ni vacío

public record AuthDTO( // Record para la autenticación de usuario
                       @NotBlank @Email
                       String email,
                       @NotBlank
                       String password
) {
}
