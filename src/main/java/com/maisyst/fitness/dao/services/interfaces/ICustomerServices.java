package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;

import java.util.UUID;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,UUID>{
    MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, UUID activity_id, CustomerModel model);
    MaiResponse<CustomerModel> findByUsername(String username);

}
