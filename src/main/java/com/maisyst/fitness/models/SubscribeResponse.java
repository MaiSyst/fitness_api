package com.maisyst.fitness.models;

import java.sql.Date;

public record SubscribeResponse(String subscribeId,Date dateStart,
                                Date dateEnd,boolean isActive,
                                String subscriptionType) {}
