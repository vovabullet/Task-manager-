package com.example.taskproject.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // генерация секретного ключа

    // генерация JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // устанавливается имя пользователя
                .setIssuedAt(new Date()) // утсанавливается время создания токена
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // срок дейтсвия: 10 часов
                .signWith(secretKey) // токен подписывается секретным ключом
                .compact(); // завершение создания токена
    }

    // проверка токена и извлечение имени пользователя
    public String validateTokenAndGetUsername (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token) // парсится токен и проверяется подпись
                .getBody()
                .getSubject(); // извлекается поле "subject" (имя пользователя)
    }
}
