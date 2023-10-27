package com.maisyst.fitness.models;

public record AuthResponse(String username,String authToken,AuthRole role,boolean isActive) {
}
