package com.example.taskproject.models;

import com.example.taskproject.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Size(max = 255)
    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 4, max = 100, message = "Password must be between 8 and 50 characters")
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING) // Хранение значения enum в строковом виде
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    // получение логина
    @Override
    public String getUsername() {
        return email; // email используется как логин
    }

    // получение пароля
    @Override
    public String getPassword() {
        return password;
    }

    // срок действия аккаунта НЕ истёк (аккаунт активен)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // учётная запись не заблокирована
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // срок учётной записи не истёк
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // учётная запись активна
    @Override
    public boolean isEnabled() {
        return true;
    }
}