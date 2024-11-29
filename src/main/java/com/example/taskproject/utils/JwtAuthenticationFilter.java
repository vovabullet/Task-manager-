package com.example.taskproject.utils;

import com.example.taskproject.models.User;
import com.example.taskproject.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // извлечение заголовка Authorization
        String authorizationHeader = request.getHeader("Authorization");

        // проверка наличия токена
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // убирается префикс "Bearer "
            String username = jwtUtil.validateTokenAndGetUsername(token); // проверка токена и получение username

            // Установка аутентификации в SecurityContext
            if (username != null) {
                // получение пользователя из БД
                User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

                // преобразование ролей в GrantedAuthority
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().getName()));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // передача запроса дальше в фильтр
        filterChain.doFilter(request, response);
    }

}
