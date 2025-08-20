package com.foro.forum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foro.forum.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(User user) {
        try {
            System.out.println("DEBUG: Generando token para usuario: " + user.getEmail());
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("API Foro Alura")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            System.out.println("DEBUG: Token generado: " + token);
            return token;
        } catch (JWTCreationException exception){
            System.err.println("ERROR: Fallo al generar token JWT: " + exception.getMessage());
            throw new RuntimeException("Error al generar token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null || token.trim().isEmpty()) {
            System.err.println("ERROR: Token JWT es nulo o vacío en getSubject.");
            throw new RuntimeException("Token JWT no puede ser nulo o vacío.");
        }
        try {
            System.out.println("DEBUG: Verificando token: " + token);
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("API Foro Alura")
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            System.out.println("DEBUG: Token verificado. Sujeto: " + subject);
            return subject;
        } catch (JWTVerificationException exception) {
            System.err.println("ERROR: Fallo de verificación JWT para token: " + token + " - Mensaje: " + exception.getMessage());
            throw new RuntimeException("Token JWT inválido o expirado.", exception);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
