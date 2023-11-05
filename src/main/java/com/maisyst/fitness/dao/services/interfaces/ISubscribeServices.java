package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.models.SubscriptionModel;

import java.util.List;
import java.util.UUID;

public interface ISubscribeServices{
    MaiResponse<SubscribeModel> insert(SubscribeModel model);
     MaiResponse<SubscribeModel> updateCustomerSubscription(String subscribeId, SubscriptionModel model);
    MaiResponse<SubscribeModel> updateSubscribeCustomer(String subscribeId, CustomerModel model);
    MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer();
    MaiResponse<String> deleteById(String id);
    MaiResponse<SubscribeModel> findById(String id);

    MaiResponse<List<SubscribeModel>> fetchAll();

    MaiResponse<String> insertMany(List<SubscribeModel> models);

    MaiResponse<String> deleteMany(List<String> ids);
    MaiResponse<SubscribeModel> update(String id, SubscribeModel model);

}
