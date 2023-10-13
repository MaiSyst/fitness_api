package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ISubscribeServices;
import com.maisyst.fitness.dao.repositories.ISubscribeRepository;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.models.SubscriptionModel;
import com.maisyst.fitness.utils.TypeSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public MaiResponse<String> deleteById(Integer id) {
        try {
            subscribeRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Subscribe has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscribeModel> findById(Integer id) {
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
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        try {
            subscribeRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Subscribes has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscribeModel> update(Integer id, SubscribeModel model) {
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
    public MaiResponse<SubscribeModel> updateCustomerSubscription(Integer subscribeId, SubscriptionModel model) {
        return null;
    }

    @Override
    public MaiResponse<SubscribeModel> updateSubscribeCustomer(Integer subscribeId, CustomerModel model) {
        return null;
    }

    @Override
    public MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer() {
        try {
            System.out.println("SubServices");
            final String query = "SELECT * from subscribe,customer as c,subscription as sub WHERE subscribe.subscription_id=sub.subscription_id and subscribe.customer_id=c.customer_id";
            var response = jdbcTemplate.query(query,
                    (rs, rows) -> {
                        CustomerModel customerModel = new CustomerModel(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("year_of_birth"), rs.getString("address"));
                        SubscriptionModel subscriptionModel = new SubscriptionModel(rs.getString("subscription_id"), rs.getString("label"), rs.getDouble("price"), stringToTypeSubscription(rs.getString("type")));
                        return new SubscribeModel(
                                rs.getInt("subscribe_id"),
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
