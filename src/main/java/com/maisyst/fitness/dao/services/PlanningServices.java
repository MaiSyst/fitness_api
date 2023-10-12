package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.interfaces.IPlanningServices;
import com.maisyst.fitness.dao.repositories.CoachRepository;
import com.maisyst.fitness.dao.repositories.PlanningRepository;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanningServices implements IPlanningServices {
    private final PlanningRepository planningRepository;
    public PlanningServices(PlanningRepository planningRepository){
        this.planningRepository=planningRepository;
    }

    @Override
    public MaiResponse<PlanningModel> insert(PlanningModel model) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<PlanningModel> findById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<List<PlanningModel>> fetchAll() {
        return null;
    }

    @Override
    public MaiResponse<String> insertMany(List<PlanningModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        return null;
    }

    @Override
    public MaiResponse<PlanningModel> update(Integer id, PlanningModel model) {
        return null;
    }
}
