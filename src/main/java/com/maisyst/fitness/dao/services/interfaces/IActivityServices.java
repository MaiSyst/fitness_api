package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public non-sealed interface IActivityServices extends IServices<ActivityModel, UUID> {
    MaiResponse<ActivityModel> insertWithSubscription(String type, ActivityModel model);

    MaiResponse<List<ActivityModel>> fetchAllWithCoachSubsPlanning();

    MaiResponse<List<ActivityModel>> searchIt(String query);

}
