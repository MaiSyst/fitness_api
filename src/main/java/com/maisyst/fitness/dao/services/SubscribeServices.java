package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.interfaces.ISubscribeServices;
import com.maisyst.fitness.dao.repositories.CoachRepository;
import com.maisyst.fitness.dao.repositories.SubscribeRepository;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.models.SubscriptionModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeServices implements ISubscribeServices {
    private final SubscribeRepository subscribeRepository;
    public SubscribeServices(SubscribeRepository subscribeRepository){
        this.subscribeRepository=subscribeRepository;
    }

    @Override
    public MaiResponse<SubscribeModel> insert(SubscribeModel model) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<SubscribeModel> findById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<List<SubscribeModel>> fetchAll() {
        return null;
    }

    @Override
    public MaiResponse<String> insertMany(List<SubscribeModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        return null;
    }

    @Override
    public MaiResponse<SubscribeModel> update(Integer id, SubscribeModel model) {
        return null;
    }
}
