package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.models.SubscriptionModel;

import java.util.List;

public interface ISubscribeServices{
    MaiResponse<SubscribeModel> insert(SubscribeModel model);
     MaiResponse<SubscribeModel> updateCustomerSubscription(int subscribeId, SubscriptionModel model);
    MaiResponse<SubscribeModel> updateSubscribeCustomer(int subscribeId, CustomerModel model);
    MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer();
    MaiResponse<String> deleteById(int id);
    MaiResponse<SubscribeModel> findById(int id);

    MaiResponse<List<SubscribeModel>> fetchAll();

    MaiResponse<String> insertMany(List<SubscribeModel> models);

    MaiResponse<String> deleteMany(List<Integer> ids);
    MaiResponse<SubscribeModel> update(int id, SubscribeModel model);

}
