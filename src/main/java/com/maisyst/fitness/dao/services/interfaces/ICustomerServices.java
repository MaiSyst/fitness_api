package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;

import java.util.UUID;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,String>{
    MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, UUID activity_id, CustomerModel model);
    MaiResponse<CustomerModel> findByUsername(String username);
    MaiResponse<CustomerModel> update(UUID id,String subscriptionType,String customerId, CustomerModel model);
}
