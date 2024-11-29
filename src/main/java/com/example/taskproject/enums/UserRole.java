package com.example.taskproject.enums;


public enum UserRole {
    ROLE_USER, ROLE_ADMIN;

    public String getName() {
        return name();
    }

    // преобразование строки в enum с обработкой ошибок
    public static UserRole fromString(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role +
                    ". Allowed values are ROLE_USER or ROLE_ADMIN.");
        }
    }
}
