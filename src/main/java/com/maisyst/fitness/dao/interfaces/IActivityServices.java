package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.TypeSubscription;

public non-sealed interface IActivityServices extends IServices<ActivityModel,Integer>{
    MaiResponse<ActivityModel> insertWithSubscription(String type,ActivityModel model);
}
