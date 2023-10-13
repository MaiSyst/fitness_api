package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.models.SubscriptionModel;

import java.util.List;

public non-sealed interface ISubscribeServices extends IServices<SubscribeModel,Integer>{
     MaiResponse<SubscribeModel> updateCustomerSubscription(Integer subscribeId, SubscriptionModel model);
    MaiResponse<SubscribeModel> updateSubscribeCustomer(Integer subscribeId, CustomerModel model);
    MaiResponse<List<SubscribeModel>> findAllWithSubscriptionAndCustomer();

}
