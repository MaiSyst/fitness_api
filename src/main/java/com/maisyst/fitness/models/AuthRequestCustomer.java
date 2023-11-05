package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.AuthRole;

public record AuthRequestCustomer(String username, String password, AuthRole role) {
}
