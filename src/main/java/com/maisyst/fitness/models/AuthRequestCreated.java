package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.AuthRole;

import java.sql.Date;

public record AuthRequestCreated(
        String firstName,
        String lastName,
        Date date,
         String address,
         String phoneNumber,
        String username,
        String password,
        String roomId
        ) {
}
