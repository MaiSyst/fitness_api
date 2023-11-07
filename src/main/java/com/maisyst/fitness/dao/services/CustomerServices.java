package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.IActivityServices;
import com.maisyst.fitness.dao.services.interfaces.ICustomerServices;
import com.maisyst.fitness.dao.services.interfaces.ISubscribeServices;
import com.maisyst.fitness.dao.services.interfaces.ISubscriptionServices;
import com.maisyst.fitness.dao.repositories.ICustomerRepository;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.maisyst.fitness.utils.MaiUtils.getDateSubscription;
import static com.maisyst.fitness.utils.MaiUtils.stringToTypeSubscription;

@Service
public class CustomerServices implements ICustomerServices {
    private final ICustomerRepository customerRepository;
    private final ISubscribeServices subscribeServices;
    private final ISubscriptionServices subscriptionServices;
    private final IActivityServices activityServices;
    private final JdbcTemplate jdbcTemplate;
    public CustomerServices(ICustomerRepository customerRepository, ISubscribeServices subscribeServices,
                            ISubscriptionServices subscriptionServices, IActivityServices activityServices,JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.subscribeServices = subscribeServices;
        this.subscriptionServices = subscriptionServices;
        this.activityServices = activityServices;
        this.jdbcTemplate= jdbcTemplate;
    }

    @Override
    public MaiResponse<CustomerModel> insert(CustomerModel model) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, String activity_id, CustomerModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(typeSubscription);
            var activityModelMaiResponse = activityServices.findById(activity_id);
            if (subscriptionResponse.getStatus() == HttpStatus.OK && activityModelMaiResponse.getStatus() == HttpStatus.OK) {
                //Date yearOfBirth, String address,String email,String password
                var customerResponse = customerRepository.save(new CustomerModel(
                        model.getFirstName(),
                        model.getLastName(),
                        model.getYearOfBirth(),
                        model.getAddress())
                );
                var moment = getDateSubscription(stringToTypeSubscription(typeSubscription));
                subscriptionResponse.getData().getActivities().add(activityModelMaiResponse.getData());
                var subscribeRes = subscribeServices.insert(new SubscribeModel(moment[0], moment[1], true, customerResponse, subscriptionResponse.getData()));
                if (subscribeRes.getStatus() == HttpStatus.OK) {
                    System.out.println(activity_id+" su="+subscriptionResponse.getData().getSubscriptionId());
                        jdbcTemplate.update("INSERT INTO concern VALUES(?,?)", activity_id, subscriptionResponse.getData().getSubscriptionId());
                    return new MaiResponse.MaiSuccess<>(customerResponse, HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>(subscribeRes.getMessage(), HttpStatus.NOT_FOUND);
                }
            } else {
                var error = subscriptionResponse.getMessage() == null || subscriptionResponse.getMessage().isBlank() ? activityModelMaiResponse.getMessage() : subscriptionResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        try {
            var customerResponse = customerRepository.findById(id);
            if (customerResponse.isPresent()) {
                customerRepository.deleteById(id);
                return new MaiResponse.MaiSuccess<>("Customer have been deleted", HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer haven't been deleted", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> findById(String id) {
        try {
            var customerResponse = customerRepository.findById(id);
            if (customerResponse.isPresent()) {
                return new MaiResponse.MaiSuccess<>(customerResponse.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer don't exist", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<CustomerModel>> fetchAll() {
        try {
            var response = customerRepository.findAll();
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<CustomerModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        return null;
    }


    @Override
    public MaiResponse<CustomerModel> update(String activityId, String subscriptionType, String customerId, CustomerModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(subscriptionType);
            var activityModelMaiResponse = activityServices.findById(activityId);
            if (subscriptionResponse.getStatus() == HttpStatus.OK && activityModelMaiResponse.getStatus() == HttpStatus.OK) {
                var responseCustomer = customerRepository.findById(customerId);
                if (responseCustomer.isPresent()) {

                    var moment = getDateSubscription(stringToTypeSubscription(subscriptionType));

                    subscriptionResponse.getData().getActivities().add(activityModelMaiResponse.getData());
                    responseCustomer.get().setAddress(model.getAddress());
                    responseCustomer.get().setFirstName(model.getFirstName());
                    responseCustomer.get().setLastName(model.getLastName());
                    responseCustomer.get().setYearOfBirth(model.getYearOfBirth());
                    customerRepository.save(responseCustomer.get());

                    //var subscribeRes = subscribeServices.updateSubscribeCustomer(customerId,new SubscribeModel(su,moment[0], moment[1], responseCustomer.get().getIsActive(), responseCustomer.get(), subscriptionResponse.getData()));
                    return new MaiResponse.MaiSuccess<>(responseCustomer.get(), HttpStatus.OK);
//                    if (subscribeRes.getStatus() == HttpStatus.OK) {
//
//                    } else {
//                        return new MaiResponse.MaiError<>(subscribeRes.getMessage(), HttpStatus.NOT_FOUND);
//                    }
                } else {
                    return new MaiResponse.MaiError<>("Customer not found.", HttpStatus.NOT_FOUND);
                }
            } else {
                var error = subscriptionResponse.getMessage() == null || subscriptionResponse.getMessage().isBlank() ? activityModelMaiResponse.getMessage() : subscriptionResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> update(String id, CustomerModel model) {
        return null;
    }
}
