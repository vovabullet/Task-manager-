package com.example.taskproject.config;

import com.example.taskproject.utils.JwtAuthenticationFilter;
import com.example.taskproject.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // конфигурация фильтров и маршрутов
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF отключается для REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // доступ к маршрутам аутентификации открыт для всех
                        .requestMatchers("/admin/**").hasRole("ADMIN") // доступ к маршрутам /admin только для ADMIN
                        .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN") // Доступ к маршрутам пользователей
                        .anyRequest().authenticated() // остальные запросы требуют авторизации
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // без сессий, так как REST API с JWT не использует сессии.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT-фильтр

        return http.build();
    }

    // шифрование пароля
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // используется для обработки логики аутентификации
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration,
            UserDetailsService userDetailsService) throws Exception {
        return configuration.getAuthenticationManager();
    }

}