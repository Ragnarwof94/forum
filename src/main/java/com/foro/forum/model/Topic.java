package com.foro.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "topics") // Mapea a la tabla 'topics' en la base de datos
@Entity(name = "Topic") // Nombre de la entidad JPA
@Getter // Lombok: genera getters automáticamente
@Setter // Lombok: genera setters automáticamente
@NoArgsConstructor // Lombok: genera constructor sin argumentos
@AllArgsConstructor // Lombok: genera constructor con todos los argumentos
@EqualsAndHashCode(of = "id") // Lombok: genera equals y hashCode basado en el ID
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String message;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "course_name", nullable = false)
    private String courseName;
    private String status; // Por ejemplo: "OPEN", "CLOSED"

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno: muchos tópicos pueden ser creados por un mismo usuario
    @JoinColumn(name = "user_id", nullable = false) // Clave foránea al ID del usuario
    private User author; // Usuario que creó el tópico

    // Constructor para cuando se crea un tópico desde un DTO
    public Topic(String title, String message, String courseName, User author) {
        this.title = title;
        this.message = message;
        this.creationDate = LocalDateTime.now(); // Fecha de creación automática
        this.courseName = courseName;
        this.status = "OPEN"; // Estado inicial del tópico
        this.author = author;
    }

    // Métodos para actualizar el tópico (si se necesitara en futuras funcionalidades)
    public void updateTopic(String newTitle, String newMessage, String newCourseName) {
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            this.title = newTitle;
        }
        if (newMessage != null && !newMessage.trim().isEmpty()) {
            this.message = newMessage;
        }
        if (newCourseName != null && !newCourseName.trim().isEmpty()) {
            this.courseName = newCourseName;
        }
    }

    // Método para cambiar el estado del tópico a "CLOSED"
    public void closeTopic() {
        this.status = "CLOSED";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStatus() {
        return status;
    }

    public User getAuthor() {
        return author;
    }
}
