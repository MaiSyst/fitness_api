package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;

import java.util.UUID;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,String>{
    MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, String activity_id, CustomerModel model);
    MaiResponse<CustomerModel> update(String id,String subscriptionType,String customerId, CustomerModel model);
}
