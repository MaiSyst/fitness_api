package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.IPlanningServices;
import com.maisyst.fitness.dao.repositories.IPlanningRepository;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.MaiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanningServices implements IPlanningServices {
    private final IPlanningRepository planningRepository;
    private final ActivityServices activityServices;
    private final RoomServices roomServices;
    private final JdbcTemplate jdbcTemplate;


    public PlanningServices(IPlanningRepository planningRepository, ActivityServices activityServices, RoomServices roomServices, JdbcTemplate jdbcTemplate) {
        this.planningRepository = planningRepository;
        this.activityServices = activityServices;
        this.roomServices = roomServices;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MaiResponse<PlanningModel> insert(int activity_id, String room_id, PlanningModel model) {
        try {
            var activityResponse = activityServices.findById(activity_id);
            var roomResponse = roomServices.findById(room_id);
            if (activityResponse.getStatus() == HttpStatus.OK && roomResponse.getStatus() == HttpStatus.OK) {
                model.setActivity(activityResponse.getData());
                model.setRoom(roomResponse.getData());
                return new MaiResponse.MaiSuccess<>(planningRepository.save(model), HttpStatus.OK);
            } else {
                var error = activityResponse.getMessage() == null || activityResponse.getMessage().isBlank() ? roomResponse.getMessage() : activityResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(int id) {
        try {
            planningRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Planning have been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<PlanningModel> findById(int id) {
        try {
            var response = planningRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Planning not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
        }
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
    public MaiResponse<PlanningModel> update(int id, String room_id, PlanningModel model) {
        return null;
    }

    @Override
    public MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom() {
        try {
            String query = "SELECT * FROM planning,room,activity WHERE planning.activity_id=activity.activity_id and room.room_id=planning.room_id";
            var responsePlanningModel = jdbcTemplate.query(query, (rs, rows) -> {

                List<CoachModel> coachModels = jdbcTemplate.query(
                        "SELECT * FROM coach WHERE coach.activity_id=" + rs.getInt("activity_id"),
                        (rsCoach, rowsCoach) -> new CoachModel(
                                rsCoach.getInt("coach_id"),
                                rsCoach.getString("first_name"),
                                rsCoach.getString("last_name"),
                                rsCoach.getString("phone"),
                                rsCoach.getString("address"),
                                rsCoach.getString("speciality")
                        )
                );
                List<SubscriptionModel> subscriptionModels = jdbcTemplate.query(
                        "SELECT * FROM subscription,concern WHERE subscription.subscription_id=concern.subscription_id and concern.activity_id=" + rs.getInt("activity_id"),
                        (rsCoach, rowsCoach) -> new SubscriptionModel(
                                rsCoach.getString("subscription_id"),
                                rsCoach.getString("label"),
                                rsCoach.getDouble("price"),
                                MaiUtils.stringToTypeSubscription(rsCoach.getString("type"))
                        )
                );
                ActivityModel activityModel = new ActivityModel(
                        rs.getInt("activity_id"),
                        rs.getString("label"),
                        rs.getString("description"),
                        coachModels,
                        subscriptionModels);
                RoomModel roomModel = new RoomModel(
                        rs.getString("room_id"),
                        rs.getString("room_name")
                );
                return new PlanningModel(
                        rs.getInt("planning_id"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        roomModel,
                        activityModel
                );
            });
            return new MaiResponse.MaiSuccess<>(responsePlanningModel, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.OK);
        }
    }
}
