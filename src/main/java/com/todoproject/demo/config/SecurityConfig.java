package com.todoproject.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Lee la API_KEY de las variables de entorno
    private static final String ACCESS_TOKEN = System.getenv("API_KEY") != null
            ? System.getenv("API_KEY")
            : "TEAM123";  // Default para desarrollo local


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/welcome", "/login", "/error", "/favicon.ico").permitAll()
                        .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().disable() // Deshabilita completamente formLogin
                .addFilterBefore(new ApiKeyFilter(ACCESS_TOKEN), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}