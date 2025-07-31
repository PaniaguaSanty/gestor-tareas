package com.todoproject.demo.service;

import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oauth = super.loadUser(userRequest);
            String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
            logger.info("Proveedor detectado: {}", provider);
            Map<String, Object> attrs = new HashMap<>(oauth.getAttributes());  // Clonamos para poder modificar

            final String email;
            final String name;
            final String avatarUrl;
            final String usernameAttrKey;

            switch (provider) {
                case "GOOGLE" -> {
                    email = (String) attrs.get("email");
                    name = (String) attrs.get("name");
                    avatarUrl = (String) attrs.get("picture");
                    usernameAttrKey = "email";
                }
                case "GITHUB" -> {
                    String ghLogin = (String) attrs.get("login");
                    String ghEmail = (String) attrs.get("email");
                    String ghName = (String) attrs.get("name");

                    email = (ghEmail != null && !ghEmail.isEmpty())
                            ? ghEmail
                            : ghLogin + "@github.local";
                    name = (ghName != null && !ghName.isEmpty())
                            ? ghName
                            : ghLogin;
                    avatarUrl = (String) attrs.get("avatar_url");
                    usernameAttrKey = "login";
                }
                default -> throw new OAuth2AuthenticationException(
                        new OAuth2Error("invalid_provider"),
                        "Proveedor no soportado: " + provider
                );
            }

            User user = repo.findByEmail(email)
                    .map(existingUser -> {
                        logger.info("Actualizando usuario existente: {}", email);
                        existingUser.setName(name);
                        existingUser.setProvider(provider);
                        existingUser.setAvatarUrl(avatarUrl);
                        return repo.save(existingUser);
                    })
                    .orElseGet(() -> {
                        logger.info("Registrando nuevo usuario: {}", email);
                        User newUser = new User();
                        newUser.setEmail(email);

                        // Generar username seguro
                        String cleanBase = email.split("@")[0].replaceAll("[^a-zA-Z0-9]", "");
                        String usernameBase = cleanBase.length() > 20
                                ? cleanBase.substring(0, 20)
                                : cleanBase;
                        String uniqueUsername = usernameBase + "_" + UUID.randomUUID().toString().substring(0, 8);

                        newUser.setUsername(uniqueUsername);
                        newUser.setName(name);
                        newUser.setAvatarUrl(avatarUrl);
                        newUser.setProvider(provider);
                        newUser.setActive(true);
                        newUser.setRoles(List.of("ROLE_USER"));

                        String randomPwd = UUID.randomUUID().toString();
                        newUser.setPassword(passwordEncoder.encode(randomPwd));

                        return repo.save(newUser);
                    });

            // —————————————————————————————————————————————————————
            // Inyectamos el username real en los atributos para que
            // principal.getName() lo devuelva
            attrs.put("username", user.getUsername());
            // Y usamos "username" como llave de nombre de usuario:
            return new DefaultOAuth2User(
                    user.getAuthorities(),
                    attrs,
                    "username"
            );
            // —————————————————————————————————————————————————————

        } catch (DataIntegrityViolationException e) {
            logger.error("Error de integridad en BD", e);
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("database_error"),
                    "Error al guardar usuario",
                    e
            );
        } catch (Exception e) {
            logger.error("Error inesperado en OAuth2", e);
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("server_error"),
                    "Error interno del servidor",
                    e
            );
        }
    }
}
