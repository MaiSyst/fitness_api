package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.RoomModel;
import com.maisyst.fitness.models.RoomWithTotalSubscribeResponse;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;
import java.util.UUID;

public non-sealed interface IRoomServices extends IServices<RoomModel, String>{
    MaiResponse<List<RoomWithTotalSubscribeResponse>>fetchRoomWithTotalSubscribeModel();
    MaiResponse<String> deleteByRoomName(String id);
}
