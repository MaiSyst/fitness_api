package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.IActivityServices;
import com.maisyst.fitness.dao.interfaces.ICustomerServices;
import com.maisyst.fitness.dao.interfaces.ISubscribeServices;
import com.maisyst.fitness.dao.interfaces.ISubscriptionServices;
import com.maisyst.fitness.dao.repositories.ICustomerRepository;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.TypeSubscription;
import com.maisyst.MaiDateCompare;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.maisyst.fitness.utils.MaiUtils.getDateSubscription;
import static com.maisyst.fitness.utils.MaiUtils.stringToTypeSubscription;

@Service
public class CustomerServices implements ICustomerServices {
    private final ICustomerRepository customerRepository;
    private final ISubscribeServices subscribeServices;
    private final ISubscriptionServices subscriptionServices;
    private final IActivityServices activityServices;

    public CustomerServices(ICustomerRepository customerRepository, ISubscribeServices subscribeServices, ISubscriptionServices subscriptionServices, IActivityServices activityServices) {
        this.customerRepository = customerRepository;
        this.subscribeServices = subscribeServices;
        this.subscriptionServices = subscriptionServices;
        this.activityServices = activityServices;
    }

    @Override
    public MaiResponse<CustomerModel> insert(CustomerModel model) {
        try {
            var customerResponse = customerRepository.save(model);
            return new MaiResponse.MaiSuccess<>(customerResponse, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, int activity_id, CustomerModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(typeSubscription);
            var activityModelMaiResponse = activityServices.findById(activity_id);
            if (subscriptionResponse.getStatus() == HttpStatus.OK && activityModelMaiResponse.getStatus() == HttpStatus.OK) {
                var customerResponse = customerRepository.save(model);
                var moment = getDateSubscription(stringToTypeSubscription(typeSubscription));
                subscriptionResponse.getData().getActivities().add(activityModelMaiResponse.getData());
                var subscribeRes = subscribeServices.insert(new SubscribeModel(moment[0], moment[1], true, customerResponse, subscriptionResponse.getData()));
                if (subscribeRes.getStatus() == HttpStatus.OK) {
                    return new MaiResponse.MaiSuccess<>(customerResponse, HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>(subscribeRes.getMessage(), HttpStatus.NOT_FOUND);
                }
            } else {
                var error = subscriptionResponse.getMessage()==null||subscriptionResponse.getMessage().isBlank()? activityModelMaiResponse.getMessage() : subscriptionResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> findById(Integer id) {
        return null;
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
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> update(Integer id, CustomerModel model) {
        return null;
    }
}
