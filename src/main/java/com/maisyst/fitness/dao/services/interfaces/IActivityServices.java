package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;

public non-sealed interface IActivityServices extends IServices<ActivityModel, String> {
    MaiResponse<ActivityModel> insertWithSubscription(String type, ActivityModel model);

    MaiResponse<List<ActivityModel>> searchIt(String query);

}
