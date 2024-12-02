# Task-manager-

## Описание проекта

Этот проект представляет собой RESTful API для управления задачами. Он поддерживает создание, обновление и удаление пользователей, задач и комментариев. Система включает уровни авторизации и аутентификации, обеспечивает пагинацию данных и предоставляет документацию API через Swagger.

---

## Возможности

- **Управление задачами**: создание, обновление, удаление и назначение задач.
- **Управление пользователями**: создание, редактирование, удаление и изменение ролей пользователей.
- **Комментарии**: добавление комментариев к задачам и получение всех комментариев.
- **Авторизация**: основана на ролях (`ROLE_USER`, `ROLE_ADMIN`).
- **Пагинация**: поддержка вывода данных страницами.
- **Документация API**: реализована через OpenAPI/Swagger UI.

---

## Технологии

- **Язык**: Java 17
- **Фреймворки**:
    - Spring Boot (Web, Data JPA, Security, Validation)
    - Hibernate
    - Springdoc OpenAPI
- **База данных**: PostgreSQL
- **Контейнеризация**: Docker, Docker Compose
- **Тестирование**: JUnit 5, Mockito

---

## Установка и локальный запуск

### 1. Клонирование репозитория


``` bash
git clone https://github.com/vovabullet/Task-manager-.git
```

### 2. Настройка базы данных

Проект использует PostgreSQL в контейнере. Настройка базы данных определяется в `docker-compose.yml`.

#### Конфигурация в `docker-compose.yml`:

``` yaml
version: '3.8'  
  
services:  
  postgres:  
    image: postgres:15  
    container_name: postgres_container  
    restart: always  
    environment:  
      POSTGRES_USER: postgres  
      POSTGRES_PASSWORD: postgres  
      POSTGRES_DB: taskdb  
    ports:  
      - "5432:5432"  
    volumes:  
      - postgres_data:/var/lib/postgresql/data  
  
volumes:  
  postgres_data:
```
### 3. Настройка `application.properties`

Проект использует настройки подключения, указанные в `src/main/resources/application.properties`.

#### Пример:

``` properties
# DB connect  
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb  
spring.datasource.username = postgres  
spring.datasource.password = postgres  
spring.datasource.driver-class-name=org.postgresql.Driver  
  
# JPA/Hibernate  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.format_sql = TRUE  
spring.jpa.properties.hibernate.show_sql = TRUE  
  
# Logging  
logging.level.org.hibernate.SQL=DEBUG  
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE  
logging.level.org.springframework.security=DEBUG  
  
# Security  
spring.security.user.name=admin  
spring.security.user.password=admin
```
### 4. Сборка и запуск проекта

#### С использованием Docker Compose:

``` bash
docker-compose up --build
```

---

## Документация API

После запуска приложения документация API доступна по адресу:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Пример эндпоинтов:

- **POST /users/create**: Создать нового пользователя.
- **GET /tasks/{id}**: Получить задачу по ID.
- **PUT /tasks/{id}/status**: Обновить статус задачи.
- **POST /tasks/{id}/comments**: Добавить комментарий к задаче.

---

## Инструкция для тестирования

### 1. Запуск тестов

Тесты находятся в директории `src/test/java`. Для запуска выполните:

``` bash
./mvnw test
```
### 2. Типы тестов

- **Юнит-тесты**: проверка сервисов и логики.
- **Интеграционные тесты**: проверка взаимодействия с базой данных.

---

## Структура проекта

``` bash
taskProject/
│
├── src/main/java/com/example/taskproject/
│   ├── config/            # конфигурация
│   ├── controllers/       # REST-контроллеры
│   ├── enums/             # Перечисления (роли, статусы, приоритеты)
│   ├── models/            # Сущности базы данных
│   ├── repositories/      # Репозитории Spring Data JPA
│   ├── services/          # Логика приложения
│   ├── utils/             # Утилиты
│   └── TaskProjectApplication.java  # Точка входа
│
├── src/main/resources/
│   └── application.properties  # Основные настройки
│
├── src/test/java/com/example/taskproject/
│   ├── controllers/       # Тесты контроллеров
│   ├── services/          # Тесты сервисов
│   └── TaskIntegrationTest.java     # Интеграционные тесты
│
├── docker-compose.yml      # Настройки контейнеризации
└── README.md               # Документация проекта
```
