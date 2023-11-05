package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.ISubscribeServices;
import com.maisyst.fitness.dao.repositories.ISubscribeRepository;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.maisyst.fitness.utils.MaiUtils.stringToMaiDay;
import static com.maisyst.fitness.utils.MaiUtils.stringToTypeSubscription;

@Service
public class SubscribeServices implements ISubscribeServices {
    private final ISubscribeRepository subscribeRepository;
    private final JdbcTemplate jdbcTemplate;

    public SubscribeServices(ISubscribeRepository subscribeRepository, JdbcTemplate jdbcTemplate) {
        this.subscribeRepository = subscribeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MaiResponse<SubscribeModel> insert(SubscribeModel model) {
        try {
            return new MaiResponse.MaiSuccess<>(subscribeRepository.save(model), HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        try {
            subscribeRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Subscribe has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscribeModel> findById(String id) {
        try {
            var response = subscribeRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Subscribe don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<SubscribeModel>> fetchAll() {
        try {
            return new MaiResponse.MaiSuccess<>(subscribeRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<SubscribeModel> models) {
        try {
            subscribeRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Subscribe has been added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        try {
            subscribeRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Subscribes has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscribeModel> update(String id, SubscribeModel model) {
        try {
            var result = subscribeRepository.findById(id);
            if (result.isPresent()) {
                result.get().setCustomer(model.getCustomer());
                result.get().setSubscription(model.getSubscription());
                var response = subscribeRepository.save(result.get());
                return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Subscribe id don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscribeModel> updateCustomerSubscription(String subscribeId, SubscriptionModel model) {
        return null;
    }

    @Override
    public MaiResponse<SubscribeModel> updateSubscribeCustomer(String subscribeId, CustomerModel model) {
        return null;
    }

    @Override
    public MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer() {
        try {
            System.out.println("SubServices");
            final String query = "SELECT * from subscribe,customer as c,subscription as sub WHERE subscribe.subscription_id=sub.subscription_id and subscribe.customer_id=c.customer_id";
            var response = jdbcTemplate.query(query,
                    (rs, rows) -> {
                        CustomerModel customerModel = new CustomerModel(
                                rs.getString("customer_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDate("year_of_birth"),
                                rs.getString("address"),
                                rs.getString("email"),
                                rs.getString("password")
                        );

                        SubscriptionModel subscriptionModel = new SubscriptionModel(rs.getString("subscription_id"), rs.getString("label"), rs.getDouble("price"), stringToTypeSubscription(rs.getString("type")));
                        List<ActivityModel> activities = jdbcTemplate.query("SELECT * FROM concern,activity WHERE concern.activity_id=activity.activity_id AND concern.subscription_id='" + rs.getString("subscription_id") + "'",
                                (rs1, rows1) -> {
                                    ActivityModel activityModel = new ActivityModel(UUID.fromString(rs1.getString("activity_id")), rs1.getString("label"), rs1.getString("description"));
                                    var coach = jdbcTemplate.query("SELECT * FROM coach WHERE coach.activity_id='" + rs1.getInt("activity_id") + "'", (rs2, rows2) -> new CoachModel(
                                            UUID.fromString(rs2.getString("coach_id")),
                                            rs2.getString("first_name"),
                                            rs2.getString("last_name"),
                                            rs2.getString("phone"),
                                            rs2.getString("address"),
                                            rs2.getString("speciality")
                                    ));
                                    //int planningId, Date date, Time start_time, Time end_time, RoomModel room
                                    var planning = jdbcTemplate.query("SELECT * FROM planning,room WHERE planning.room_id=room.room_id AND planning.activity_id='" + rs1.getInt("activity_id") + "'", (rs2, rows2) -> new PlanningModel(
                                           UUID.fromString( rs2.getString("planning_id")),
                                            stringToMaiDay(rs2.getString("day")),
                                            rs2.getTime("start_time"),
                                            rs2.getTime("end_time"),
                                            new RoomModel(
                                                    rs2.getString("room_id"),
                                                    rs2.getString("room_name")
                                            )
                                    ));
                                    activityModel.setCoach(coach);
                                    activityModel.setPlannings(planning);
                                    return activityModel;
                                });

                        subscriptionModel.setActivities(activities);
                        return new SubscribeModel(
                                rs.getString("subscribe_id"),
                                rs.getDate("date_start"),
                                rs.getDate("date_end"),
                                rs.getBoolean("is_active"),
                                customerModel,
                                subscriptionModel);
                    });
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.OK);
        }
    }

}
