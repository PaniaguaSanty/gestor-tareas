package com.todoproject.demo.service;

import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        logger.info("Proveedor detectado: " + userRequest.getClientRegistration().getRegistrationId());
        Map<String, Object> attrs = oauth.getAttributes();

        final String email;
        final String name;
        final String avatarUrl;
        final String usernameAttrKey;  // clave para DefaultOAuth2User

        switch (provider) {
            case "GOOGLE" -> {
                email = (String) attrs.get("email");
                name = (String) attrs.get("name");
                avatarUrl = (String) attrs.get("picture");
                usernameAttrKey = "email";       // Google tiene "email"
            }
            case "GITHUB" -> {
                // GitHub no siempre expone 'email'
                String ghEmail = (String) attrs.get("email");
                String ghLogin = (String) attrs.get("login");
                email = (ghEmail != null) ? ghEmail : ghLogin + "@github.local";
                name = ghLogin;
                avatarUrl = (String) attrs.get("avatar_url");
                usernameAttrKey = "login";        // usamos 'login' como clave única
            }
            default -> throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_provider"),
                    "Proveedor OAuth2 no soportado: " + provider
            );
        }

        User user = repo.findByEmail(email)
                .map(u -> {
                    logger.info("Usuario ya existe: " + email);
                    u.setName(name);
                    u.setProvider(provider);
                    u.setAvatarUrl(avatarUrl);
                    return repo.save(u);
                })
                .orElseGet(() -> {
                    logger.info("Registrando nuevo usuario: " + email);
                    User u = new User();
                    u.setEmail(email);
                    // para localizar el username en la entidad
                    u.setUsername(email.split("@")[0]);
                    u.setName(name);
                    u.setAvatarUrl(avatarUrl);
                    u.setProvider(provider);
                    u.setActive(true);
                    u.setRoles(List.of("ROLE_USER"));

                    // contraseña aleatoria codificada
                    String randomPwd = UUID.randomUUID().toString();
                    u.setPassword(passwordEncoder.encode(randomPwd));
                    return repo.save(u);
                });

        return new DefaultOAuth2User(
                user.getAuthorities(),
                attrs,
                usernameAttrKey
        );
    }
}
