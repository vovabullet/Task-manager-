package com.example.taskproject.enums;

public enum TaskStatus {
    PENDING, // задача ожидает выполнения
    IN_PROGRESS, // задача в процессе выполнения
    COMPLETED; // задача завершена

    // преобразование строки в enum с обработкой ошибок
    public static TaskStatus fromString(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status +
                    ". Allowed values are PENDING, IN_PROGRESS, or COMPLETED.");
        }
    }
}
