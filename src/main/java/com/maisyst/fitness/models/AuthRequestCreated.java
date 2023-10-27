package com.maisyst.fitness.models;

public record AuthRequestCreated(String username, String password,AuthRole role) {
}
