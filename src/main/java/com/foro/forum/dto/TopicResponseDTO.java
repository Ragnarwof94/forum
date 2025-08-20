package com.foro.forum.dto;

import com.foro.forum.model.Topic;

import java.time.LocalDateTime;

public record TopicResponseDTO( // Record para la respuesta de un tópico
                                Long id,
                                String title,
                                String message,
                                LocalDateTime creationDate,
                                String courseName,
                                String status,
                                Long authorId,
                                String authorEmail
) {
    public TopicResponseDTO(Topic topic) { // Constructor para convertir un objeto Topic a DTO
        this(topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getCourseName(),
                topic.getStatus(),
                topic.getAuthor() != null ? topic.getAuthor().getId() : null, // Manejo de autor nulo
                topic.getAuthor() != null ? topic.getAuthor().getEmail() : null); // Manejo de autor nulo
    }
}
