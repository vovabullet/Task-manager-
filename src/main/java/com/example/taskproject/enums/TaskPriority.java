package com.example.taskproject.enums;

public enum TaskPriority {
    LOW, MEDIUM, HIGH;

    // преобразование строки в enum с обработкой ошибок
    public static TaskPriority fromString(String priority) {
        try {
            return TaskPriority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid task priority: " + priority +
                    ".  Allowed values are LOW, MEDIUM, or HIGH.");
        }
    }
}
