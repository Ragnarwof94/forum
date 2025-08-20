package com.foro.forum.filter;

import com.foro.forum.repository.UserRepository;
import com.foro.forum.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("DEBUG Filter: Encabezado Authorization recibido: " + (authHeader != null ? authHeader : "NULO"));

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            System.out.println("DEBUG Filter: Token extraído: " + token);
            try {
                String subject = tokenService.getSubject(token);
                if (subject != null) {
                    UserDetails user = userRepository.findByEmail(subject);
                    if (user != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("DEBUG Filter: Usuario autenticado con éxito: " + subject);
                    } else {
                        System.err.println("ERROR Filter: Usuario no encontrado en DB para sujeto: " + subject);
                    }
                } else {
                    System.err.println("ERROR Filter: Sujeto nulo de token.");
                }
            } catch (RuntimeException e) {
                System.err.println("ERROR Filter: Excepción al validar token: " + e.getMessage());
                // No autenticamos si hay una excepción al validar el token
                // Spring Security ya se encargará del 401 si no hay autenticación
            }
        } else {
            System.out.println("DEBUG Filter: No se encontró token Bearer en el encabezado Authorization.");
        }
        filterChain.doFilter(request, response);
    }
}
