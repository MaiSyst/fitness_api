package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.IActivityServices;
import com.maisyst.fitness.dao.repositories.IActivityRepository;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.maisyst.fitness.utils.MaiUtils.stringToTypeSubscription;

@Service
public class ActivityServices implements IActivityServices {
    private final IActivityRepository activityRepository;
    private final SubscriptionServices subscriptionServices;
    private final JdbcTemplate jdbcTemplate;

    public ActivityServices(IActivityRepository activityRepository, SubscriptionServices subscriptionServices, JdbcTemplate jdbcTemplate) {
        this.activityRepository = activityRepository;
        this.subscriptionServices = subscriptionServices;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MaiResponse<ActivityModel> insert(ActivityModel model) {
        try {
            return new MaiResponse.MaiSuccess<>(activityRepository.save(model), HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> insertWithSubscription(String type, ActivityModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(type);
            if (subscriptionResponse.getStatus() == HttpStatus.OK) {
                model.getSubscriptions().add(subscriptionResponse.getData());
                return new MaiResponse.MaiSuccess<>(activityRepository.save(model), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>(subscriptionResponse.getMessage(), HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(UUID id) {
        try {
            activityRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Activity deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> update(UUID id, ActivityModel model) {
        try {
            var result = activityRepository.findById(id);
            if (result.isPresent()) {
                result.get().setDescription(model.getDescription());
                result.get().setLabel(model.getLabel());
                var response = activityRepository.save(result.get());
                return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Activity don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> findById(UUID id) {
        try {
            var response = activityRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Activity is empty.", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<ActivityModel>> fetchAll() {
        try {
            final String query = "SELECT * from activity";
            var response = jdbcTemplate.query(query, (rs, rows) -> {
                final String queryCoach = "SELECT * from coach WHERE coach.activity_id ="+rs.getInt("activity_id");
                final String querySub = "SELECT * from subscription,concern WHERE concern.subscription_id = subscription.subscription_id and concern.activity_id="+rs.getInt("activity_id");
                final String queryPlanning = "SELECT * from planning,room WHERE room.room_id=planning.room_id and planning.activity_id ="+rs.getInt("activity_id");
                List<CoachModel> coachModel = jdbcTemplate.query(queryCoach, (rsCoach, rows1) ->
                        new CoachModel(
                                UUID.fromString(rsCoach.getString("coach_id")),
                                rsCoach.getString("first_name"),
                                rsCoach.getString("last_name"),
                                rsCoach.getString("phone"),
                                rsCoach.getString("address"),
                                rsCoach.getString("speciality")
                        )
                );
                List<SubscriptionModel> subscriptionModel = jdbcTemplate.query(querySub, (rsSub, rows1) ->
                        new SubscriptionModel(
                                rsSub.getString("subscription_id"),
                                rsSub.getString("label"),
                                rsSub.getDouble("price"),
                                stringToTypeSubscription(rsSub.getString("type"))
                        )
                );
                List<PlanningModel> planningModel = jdbcTemplate.query(queryPlanning, (rsPlanning, rows1) -> new PlanningModel(
                        UUID.fromString(rsPlanning.getString("planning_id")),
                        rsPlanning.getDate("date"),
                        rsPlanning.getTime("start_time"),
                        rsPlanning.getTime("end_time"),
                        new RoomModel(rsPlanning.getString("room_id"), rsPlanning.getString("room_name"))
                ));
                return new ActivityModel(UUID.fromString(rs.getString("activity_id")),
                        rs.getString("label"), rs.getString("description"),
                        coachModel, subscriptionModel, planningModel);
            });
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<ActivityModel> models) {
        try {
            activityRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Activities has been added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<UUID> ids) {
        try {
            activityRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Activities has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
