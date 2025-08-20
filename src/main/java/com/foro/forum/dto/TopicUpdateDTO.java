package com.foro.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para la actualización de un Tópico.
 * Los campos son opcionales aquí porque no todos los campos de un tópico
 * necesitan ser actualizados en una misma petición PUT.
 * Se usan validaciones solo si el campo no es nulo.
 */
public record TopicUpdateDTO(
        @Size(min = 5, max = 100) String title,
        @Size(min = 10, max = 500) String message,
        @Size(min = 3, max = 50) String courseName
) {
    // Los records de Java 17+ generan automáticamente constructores, getters, etc.
    // Aquí solo definimos los campos que esperamos recibir para actualizar.
}
