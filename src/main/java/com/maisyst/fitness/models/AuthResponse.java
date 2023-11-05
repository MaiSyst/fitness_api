package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.AuthRole;

public record AuthResponse(String username, String token, AuthRole role, boolean isActive) {
}
