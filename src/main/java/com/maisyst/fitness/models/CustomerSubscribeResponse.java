package com.maisyst.fitness.models;

import java.sql.Date;
import java.util.List;

public record CustomerSubscribeResponse(
        String customerId,String identityEMF,
        String firstName,String lastName,
        Date yearOfBirth,String address,
        String roomName,
        List<SubscribeResponse> subscribes) {
}
