package com.maisyst.fitness.models;

import java.util.List;

public record RoomUserResponse(String roomId, String roomName, UserResponse manager,
                               List<CustomerModel> customers, List<PlanningModel> plannings) {
}
