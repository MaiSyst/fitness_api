package com.maisyst.fitness.models;

import com.maisyst.fitness.utils.AuthRole;

import java.sql.Date;

public record UserResponse(String userId, String username, String firstName, String lastName, Date date, String address,
                           String phoneNumber, RoomModel room, boolean isActive, AuthRole role) {
}
