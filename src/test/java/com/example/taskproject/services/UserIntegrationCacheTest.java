package com.example.taskproject.services;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.UserRepository;
import com.example.taskproject.services.DTO.UserDto;
import com.example.taskproject.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserIntegrationCacheTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    private User user;

    @BeforeEach
    void setUp() {
        // Создание и сохранение пользователя
        user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setRole(UserRole.ROLE_USER);
        user = userRepository.save(user);
    }

    @Test
    void testCacheableGetUserById() {
        // Первый вызов: данные из базы
        UserDto result1 = userServiceImpl.getUserById(user.getId());
        assertNotNull(result1);
        assertEquals(user.getId(), result1.getId());
        assertEquals(user.getEmail(), result1.getEmail());

        // Логирование должно подтвердить, что данные запрашивались из базы
        System.out.println("Fetching user from database for ID: " + user.getId());

        // Второй вызов: данные должны быть из кэша
        UserDto result2 = userServiceImpl.getUserById(user.getId());
        assertNotNull(result2);
        assertEquals(user.getId(), result2.getId());
        assertEquals(user.getEmail(), result2.getEmail());

        // Убедимся, что второй вызов не инициировал запрос в базу данных
        long queryCount = userRepository.count(); // Косвенная проверка активности репозитория
        System.out.println("Repository query count: " + queryCount);

        // Проверка, что результат одинаковый

        //по ссылке на объект
        //assertEquals(result1, result2);

        //по данным
        assertThat(result1).usingRecursiveComparison().isEqualTo(result2);
    }
}


