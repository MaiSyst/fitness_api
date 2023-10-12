package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.interfaces.IRoomServices;
import com.maisyst.fitness.dao.repositories.CoachRepository;
import com.maisyst.fitness.dao.repositories.RoomRepository;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.RoomModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServices implements IRoomServices {
    private final RoomRepository roomRepository;
    public RoomServices(RoomRepository roomRepository){
        this.roomRepository=roomRepository;
    }

    @Override
    public MaiResponse<RoomModel> insert(RoomModel model) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        return null;
    }

    @Override
    public MaiResponse<RoomModel> findById(String id) {
        return null;
    }

    @Override
    public MaiResponse<List<RoomModel>> fetchAll() {
        return null;
    }

    @Override
    public MaiResponse<String> insertMany(List<RoomModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        return null;
    }

    @Override
    public MaiResponse<RoomModel> update(String id, RoomModel model) {
        return null;
    }
}
