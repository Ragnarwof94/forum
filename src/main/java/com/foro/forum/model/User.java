package com.foro.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users") // Mapea a la tabla 'users' en la base de datos
@Entity(name = "User") // Nombre de la entidad JPA
@Getter // Lombok: genera getters automáticamente
@NoArgsConstructor // Lombok: genera constructor sin argumentos
@AllArgsConstructor // Lombok: genera constructor con todos los argumentos
@EqualsAndHashCode(of = "id") // Lombok: genera equals y hashCode basado en el ID
public class User implements UserDetails { // Implementa UserDetails para Spring Security
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;
    @Column(unique = true, nullable = false) // El email debe ser único y no nulo
    private String email;
    @Column(nullable = false) // La contraseña no puede ser nula
    private String password;
    private String role; // Rol del usuario (ej. "USER", "ADMIN")

    // Métodos de UserDetails (requeridos por Spring Security)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por simplicidad, todos los usuarios autenticados tendrán el rol "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Usamos el email como nombre de usuario para Spring Security
    }

    // Métodos que indican si la cuenta no ha expirado, no está bloqueada, etc.
    // Para este desafío, los configuramos como true por defecto.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
