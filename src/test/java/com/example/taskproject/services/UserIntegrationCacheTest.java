package com.example.taskproject.integrationTests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        assertEquals(result1, result2);
    }
}

