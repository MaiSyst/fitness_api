package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.UUID;

public non-sealed interface IActivityServices extends IServices<ActivityModel, UUID>{
    MaiResponse<ActivityModel> insertWithSubscription(String type,ActivityModel model);
}
