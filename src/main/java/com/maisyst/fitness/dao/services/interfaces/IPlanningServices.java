package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;
import java.util.UUID;

public interface IPlanningServices{
    MaiResponse<PlanningModel> insert(UUID activity_id, String room_id, PlanningModel model);
    MaiResponse<String> deleteById(UUID id);
    MaiResponse<PlanningModel> findById(UUID id);

    MaiResponse<String> insertMany(List<PlanningModel> models);

    MaiResponse<String> deleteMany(List<UUID> ids);
    MaiResponse<PlanningModel> update(UUID activity_id,String room_id, PlanningModel model);
    MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom();
}
