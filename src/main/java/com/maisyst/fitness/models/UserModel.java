package com.maisyst.fitness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "login")
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;
    @Column(unique = true, nullable = false)
    String username;
    @Column(nullable = false)
    @JsonIgnore
    String password;
    @Column(name = "is_active")
    boolean isActive;
    @Enumerated(value = EnumType.STRING)
    AuthRole role;

    public UserModel() {
    }

    public UserModel(Integer userId, String username, String password, AuthRole role, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public UserModel(String username, String password, AuthRole role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public UserModel(Integer userId, String username, AuthRole role, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.isActive = isActive;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }
}
