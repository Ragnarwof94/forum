package com.foro.forum.service;

import com.foro.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG AuthService: Intentando cargar usuario por email: " + username);
        UserDetails userDetails = userRepository.findByEmail(username);
        if (userDetails == null) {
            System.err.println("ERROR AuthService: Usuario no encontrado para email: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
        }
        System.out.println("DEBUG AuthService: Usuario cargado con éxito: " + userDetails.getUsername());
        return userDetails;
    }
}
