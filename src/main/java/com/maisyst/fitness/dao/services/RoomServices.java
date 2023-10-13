package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.IRoomServices;
import com.maisyst.fitness.dao.repositories.IRoomRepository;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.RoomModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServices implements IRoomServices {
    private final IRoomRepository roomRepository;
    public RoomServices(IRoomRepository roomRepository){
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
