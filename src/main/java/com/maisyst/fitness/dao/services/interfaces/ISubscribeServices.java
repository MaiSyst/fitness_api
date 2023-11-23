package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;

import java.util.List;

public interface ISubscribeServices{
    MaiResponse<SubscribeModel> insert(SubscribeModel model);
    MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer();
    MaiResponse<String> deleteById(String id);
    MaiResponse<SubscribeModel> findById(String id);

    MaiResponse<List<SubscribeModel>> fetchAll();

    MaiResponse<String> insertMany(List<SubscribeModel> models);

    MaiResponse<String> deleteMany(List<String> ids);
    MaiResponse<SubscribeModel> update(String id, SubscribeModel model);
    MaiResponse<List<SubscribeModel>>findAllByCustomer(CustomerModel customer);
    MaiResponse<List<SubscribeModel>>findAllByCustomerAndIsActive(CustomerModel customer,boolean isActive);
}
