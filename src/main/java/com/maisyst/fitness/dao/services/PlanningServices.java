package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.IPlanningServices;
import com.maisyst.fitness.dao.repositories.IPlanningRepository;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiDay;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.MaiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

import static com.maisyst.fitness.utils.MaiUtils.stringToMaiDay;

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
    public MaiResponse<PlanningModel> insert(String activity_id, String room_id, PlanningModel model) {
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
    public MaiResponse<String> deleteById(String id) {
        try {
            planningRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Planning have been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<PlanningModel> findById(String id) {
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
    public MaiResponse<String> deleteMany(List<String> ids) {
        try {
            planningRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Planning have been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<PlanningModel> update(String id, String room_id, String planningId, PlanningModel model) {
        try {
            var activityResponse = activityServices.findById(id);
            var roomResponse = roomServices.findById(room_id);
            if (activityResponse.getStatus() == HttpStatus.OK && roomResponse.getStatus() == HttpStatus.OK) {
                var response = planningRepository.findById(planningId);
                if (response.isPresent()) {
                    response.get().setActivity(activityResponse.getData());
                    response.get().setRoom(roomResponse.getData());
                    response.get().setDay(model.getDay());
                    response.get().setEndTime(model.getEndTime());
                    response.get().setStartTime(model.getStartTime());
                    return new MaiResponse.MaiSuccess<>(planningRepository.save(response.get()), HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>("Planning not found", HttpStatus.NOT_FOUND);
                }
            } else {
                var error = activityResponse.getMessage() == null || activityResponse.getMessage().isBlank() ? roomResponse.getMessage() : activityResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private PlanningModel commonPartFindAllWithActivityAndRoom(String activityId,
                                                               String labelActivity,
                                                               String descActivity,
                                                               String roomId,
                                                               String roomName,

                                                               MaiDay dayPlanning,
                                                               Time startTime,
                                                               Time endTime,
                                                               String planningId
    ) {
        List<CoachModel> coachModels = jdbcTemplate.query(
                "SELECT * FROM coach WHERE coach.activity_id=?",
                (rsCoach, rowsCoach) -> new CoachModel(
                        rsCoach.getString("coach_id"),
                        rsCoach.getString("first_name"),
                        rsCoach.getString("last_name"),
                        rsCoach.getString("phone"),
                        rsCoach.getString("address"),
                        rsCoach.getString("speciality")
                ),
                activityId
        );
        List<SubscriptionModel> subscriptionModels = jdbcTemplate.query(
                "SELECT * FROM subscription,concern WHERE subscription.subscription_id=concern.subscription_id and concern.activity_id=?",
                (rsCoach, rowsCoach) -> new SubscriptionModel(
                        rsCoach.getString("subscription_id"),
                        rsCoach.getString("label"),
                        rsCoach.getInt("price"),
                        MaiUtils.stringToTypeSubscription(rsCoach.getString("type"))
                ),
                activityId
        );
        ActivityModel activityModel = new ActivityModel(
                activityId,
                labelActivity,
                descActivity,
                coachModels,
                subscriptionModels);
        RoomModel roomModel = new RoomModel(
                roomId,
                roomName
        );
        return new PlanningModel(
                planningId,
                dayPlanning,
                startTime,
                endTime,
                roomModel,
                activityModel
        );
    }
    @Override
    public MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom() {
        try {
            String query = "SELECT * FROM planning,room,activity WHERE planning.activity_id=activity.activity_id and room.room_id=planning.room_id";
            var responsePlanningModel = jdbcTemplate.query(query, (rs, rows) ->
                    commonPartFindAllWithActivityAndRoom(
                            rs.getString("activity_id"),
                            rs.getString("label"),
                            rs.getString("description"),
                            rs.getString("room_id"),
                            rs.getString("room_name"),
                            stringToMaiDay(rs.getString("day")),
                            rs.getTime("start_time"),
                            rs.getTime("end_time"),
                            rs.getString("planning_id")
                    ));
            return new MaiResponse.MaiSuccess<>(responsePlanningModel, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.OK);
        }
    }
public MaiResponse<List<PlanningModel>> findAllWithActivityAndRoom(String roomId) {
        try {
            String query = "SELECT * FROM planning,room,activity WHERE planning.activity_id=activity.activity_id and room.room_id=?";
            var responsePlanningModel = jdbcTemplate.query(query, (rs, rows) ->
                    commonPartFindAllWithActivityAndRoom(
                            rs.getString("activity_id"),
                            rs.getString("label"),
                            rs.getString("description"),
                            rs.getString("room_id"),
                            rs.getString("room_name"),
                            stringToMaiDay(rs.getString("day")),
                            rs.getTime("start_time"),
                            rs.getTime("end_time"),
                            rs.getString("planning_id")
                    ),
                    roomId
            );
            return new MaiResponse.MaiSuccess<>(responsePlanningModel, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.OK);
        }
    }

    @Override
    public MaiResponse<List<PlanningResponse>> fetchAll() {
        try {
            var response = planningRepository.findAll();
            List<PlanningResponse> data = response.stream().map(result -> new PlanningResponse(
                    result.getPlanningId(), result.getDay(), result.getStartTime(), result.getEndTime(),
                    result.getActivity().getLabel(), result.getRoom().getRoomName())).toList();
            return new MaiResponse.MaiSuccess<>(data, HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
