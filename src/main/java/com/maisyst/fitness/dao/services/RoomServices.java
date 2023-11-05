package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.IRoomServices;
import com.maisyst.fitness.dao.repositories.IRoomRepository;
import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.models.RoomWithTotalSubscribeResponse;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.RoomModel;
import com.maisyst.fitness.utils.MaiUID;
import org.hibernate.internal.util.BytesHelper;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.maisyst.fitness.utils.MaiUtils.stringToMaiDay;

@Service
public class RoomServices implements IRoomServices {
    private final IRoomRepository roomRepository;
    private final JdbcTemplate jdbcTemplate;

    public RoomServices(IRoomRepository roomRepository, JdbcTemplate jdbcTemplate) {
        this.roomRepository = roomRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MaiResponse<RoomModel> insert(RoomModel model) {
        try {
            return new MaiResponse.MaiSuccess<>(roomRepository.save(model), HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        try {
            roomRepository.deleteAllById(List.of(id));
            System.out.println(id);
            return new MaiResponse.MaiSuccess<>("Room have been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteByRoomName(String roomName) {
        try {
            roomRepository.deleteByRoomName(roomName);
            return new MaiResponse.MaiSuccess<>("Room have been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<RoomModel> findById(String id) {
        try {
            var response = roomRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Room is empty", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<RoomWithTotalSubscribeResponse>> fetchRoomWithTotalSubscribeModel() {
        try {
            String query = "SELECT * FROM room";
            var data = jdbcTemplate.query(query, (rs, rowNum) -> {
                var countSubscribe = jdbcTemplate.query("SELECT activity_id FROM planning WHERE planning.room_id=?",
                        (rs1, rowNum1) -> {
                            System.out.println("Activity=" + rs1.getString("activity_id"));
                            String query2 = "SELECT subscription_id from concern WHERE concern.activity_id=?";
                            var subscriptions = jdbcTemplate.query(query2,
                                    (rs2, rowNum2) -> {
                                        String query3 = "SELECT count(*) from subscribe WHERE subscribe.subscription_id=?";
                                        var count = jdbcTemplate.queryForObject(query3, Integer.class, UUID.nameUUIDFromBytes(rs2.getBytes("subscription_id")));
                                        return count;
                                    }, UUID.nameUUIDFromBytes(rs1.getBytes("activity_id")));
                            return subscriptions.stream().reduce(0, Integer::sum);
                        }, rs.getString("room_id"));
                var sum = countSubscribe.stream().reduce(0, Integer::sum);
                return new RoomWithTotalSubscribeResponse(
                        rs.getString("room_id"),
                        rs.getString("room_name"),
                        sum
                );
            });
            return new MaiResponse.MaiSuccess<>(data, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Error=" + ex.getMessage());
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<RoomModel>> fetchAll() {
        try {
            String query = "SELECT * FROM room";
            var data = jdbcTemplate.query(query, (rs, rowNum) -> {
                var planning = jdbcTemplate.query("SELECT * FROM planning,activity WHERE planning.activity_id =activity.activity_id AND planning.room_id='" + rs.getString("room_id") + "'",
                        (rs1, rowNum1) -> new PlanningModel(
                                UUID.fromString(rs1.getString("planning_id")),
                                stringToMaiDay(rs1.getString("day")),
                                rs1.getTime("start_time"),
                                rs1.getTime("end_time"),
                                new ActivityModel(
                                        UUID.fromString(rs1.getString("activity_id")),
                                        rs1.getString("label"),
                                        rs1.getString("description")
                                )
                        ));
                return new RoomModel(
                        rs.getString("room_id"),
                        rs.getString("room_name"),
                        planning
                );
            });
            return new MaiResponse.MaiSuccess<>(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<RoomModel> models) {
        try {
            roomRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Rooms has been added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        try {
            roomRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Rooms has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<RoomModel> update(String id, RoomModel model) {
        try {
            var result = roomRepository.findById(id);
            if (result.isPresent()) {
                result.get().setRoomName(model.getRoomName());
                var response = roomRepository.save(result.get());
                return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Room don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
