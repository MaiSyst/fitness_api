package com.maisyst.fitness.models;

public record AuthRequestCustomer(String username, String password,AuthRole role) {
}
