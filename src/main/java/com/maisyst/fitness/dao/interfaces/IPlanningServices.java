package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;

public interface IPlanningServices{
    MaiResponse<PlanningModel> insert(int activity_id,String room_id,PlanningModel model);
    MaiResponse<String> deleteById(int id);
    MaiResponse<PlanningModel> findById(int id);

    MaiResponse<String> insertMany(List<PlanningModel> models);

    MaiResponse<String> deleteMany(List<Integer> ids);
    MaiResponse<PlanningModel> update(int activity_id,String room_id, PlanningModel model);
    MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom();
}
