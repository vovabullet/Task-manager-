package com.example.taskproject.services;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.UserRepository;
import com.example.taskproject.services.DTO.UserDto;
import com.example.taskproject.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void testGetTasksById() {
        // мокирование User
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(UserRole.ROLE_USER);
        user.setPassword("password");

        // Настройка объекта UserDto
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setRole(UserRole.ROLE_USER);

        // поведение маппера
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // поведение репозитория
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // вызов метода сервиса
        UserDto result = userServiceImpl.getUserById(1L);

        // проверка результата
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(UserRole.ROLE_USER, result.getRole());

        // Убеждаемся, что метод репозитория был вызван один раз
        verify(userRepository, times(1)).findById(1L);

        // Проверяем, что маппер был вызван
        verify(modelMapper, times(1)).map(user, UserDto.class);
    }
}
