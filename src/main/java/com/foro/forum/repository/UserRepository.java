package com.foro.forum.repository;

import com.foro.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interfaz como un componente de repositorio de Spring
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar un usuario por su email. Spring Data JPA lo implementa automáticamente.
    UserDetails findByEmail(String email);
}
