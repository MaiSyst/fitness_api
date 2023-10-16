package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;

public non-sealed interface IActivityServices extends IServices<ActivityModel,Integer>{
    MaiResponse<ActivityModel> insertWithSubscription(String type,ActivityModel model);
}
