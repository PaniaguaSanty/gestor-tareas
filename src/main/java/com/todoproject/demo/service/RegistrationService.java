package com.todoproject.demo.service;

import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public RegistrationService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String email, String username, String rawPassword, String name) {
        if (repo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (repo.existsByUsername(username)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
        }

        User u = new User();
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        u.setName(name);
        u.setProvider("LOCAL");
        u.setActive(true);
        u.setRoles(List.of("ROLE_USER"));
        return repo.save(u);
    }
}
