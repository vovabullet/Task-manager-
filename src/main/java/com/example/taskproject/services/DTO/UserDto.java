package com.example.taskproject.services.DTO;

import com.example.taskproject.enums.UserRole;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    Long id;
    @NotNull
    @Size(max = 50)
    String email;
    @NotNull
    @Size(min = 4, max = 100, message = "Password must be between 8 and 50 characters")
    String password;
    @NotNull
    UserRole role;
}