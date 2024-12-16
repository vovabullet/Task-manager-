package com.example.taskproject.controllers;

import com.example.taskproject.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginPageController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginPageController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // авторизация
    @Operation(summary = "Authorization", description = "Authorization by email and password")
    @ApiResponse(responseCode = "403", description = "Email or password is incorrect")
    @ApiResponse(responseCode = "200", description = "Authorization successful")
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Генерация JWT токена
            String token = jwtUtil.generateToken(loginRequest.getEmail());

            // Установка токена в куку
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600); // Время жизни 1 час
            response.addCookie(jwtCookie);

            return ResponseEntity.ok("Authentication successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка: " + e.getMessage());
        }
    }

    // переход на страницу авторизации
    @GetMapping("/auth/login")
    public String loginPage() {
        return "login"; // возвращается html страница
    }

    // выход из аккаунта
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
        // Создаём cookie с пустым значением и временем жизни 0
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true) // Рекомендуется для HTTPS
                .path("/")
                .maxAge(0)    // Сразу истекает
                .build();

        // Добавляем cookie в заголовки ответа
        return ResponseEntity.noContent()
                .header("Set-Cookie", cookie.toString())
                .build();
    }

}

@Getter
@Setter
class LoginRequest {
    private String email;
    private String password;
}
