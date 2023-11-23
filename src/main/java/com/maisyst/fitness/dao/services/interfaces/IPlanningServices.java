package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.models.PlanningResponse;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;

public interface IPlanningServices{
    MaiResponse<List<PlanningResponse>> fetchAll();
    MaiResponse<PlanningModel> insert(String activity_id, String room_id, PlanningModel model);
    MaiResponse<String> deleteById(String id);
    MaiResponse<PlanningModel> findById(String id);

    MaiResponse<String> deleteMany(List<String> ids);
    MaiResponse<PlanningModel> update(String activity_id,String room_id,String planningId, PlanningModel model);
    MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom();

}
