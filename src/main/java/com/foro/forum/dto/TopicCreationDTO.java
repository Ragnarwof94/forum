package com.foro.forum.dto;

import jakarta.validation.constraints.NotBlank; // Para campos que no pueden ser nulos ni vacíos
import jakarta.validation.constraints.NotNull; // Para campos que no pueden ser nulos

public record TopicCreationDTO( // Record es una forma concisa de crear DTOs en Java 17+
                                @NotBlank(message = "{title.not.blank}") // Mensaje de validación personalizado
                                String title,
                                @NotBlank(message = "{message.not.blank}")
                                String message,
                                @NotBlank(message = "{courseName.not.blank}")
                                String courseName,
                                @NotNull(message = "{userId.not.null}")
                                Long userId // Para asociar el tópico a un usuario existente (ID)
) {
}
