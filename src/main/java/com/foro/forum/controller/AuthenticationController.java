package com.foro.forum.controller;

import com.foro.forum.dto.AuthDTO; // DTO para recibir credenciales de autenticación
import com.foro.forum.dto.TokenDTO; // DTO para enviar el token JWT
import com.foro.forum.model.User; // Entidad de usuario
import com.foro.forum.service.TokenService; // Servicio para generar JWT
import jakarta.validation.Valid; // Para validar los DTOs
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // Manager de autenticación de Spring Security
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Objeto para las credenciales de autenticación
import org.springframework.security.core.Authentication; // Objeto de autenticación
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/auth") // Mapea todas las peticiones a /auth
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Se inyecta el gestor de autenticación de Spring Security
    @Autowired
    private TokenService tokenService; // Se inyecta el servicio de token JWT

    @PostMapping // Maneja las peticiones POST a /auth (login)
    public ResponseEntity<TokenDTO> authenticateUser(@RequestBody @Valid AuthDTO authDTO) {
        // Crea un objeto de autenticación con el email y la contraseña recibidos
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        // Autentica al usuario usando el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(authToken);
        // Genera un token JWT para el usuario autenticado
        String jwtToken = tokenService.generateToken((User) authentication.getPrincipal());
        // Devuelve el token en la respuesta HTTP 200 OK
        return ResponseEntity.ok(new TokenDTO(jwtToken)); // Devuelve el token
    }
}
