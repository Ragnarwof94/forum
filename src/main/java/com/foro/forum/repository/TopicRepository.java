package com.foro.forum.repository;

import com.foro.forum.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interfaz como un componente de repositorio de Spring
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // JpaRepository ya proporciona métodos básicos (save, findById, findAll, deleteById, etc.)
    // Puedes añadir métodos de búsqueda personalizados si los necesitas más adelante,
    // por ejemplo: List<Topic> findByCourseName(String courseName);
}
