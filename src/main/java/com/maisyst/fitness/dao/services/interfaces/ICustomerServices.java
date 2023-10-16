package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,Integer>{
    MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription,int activity_id, CustomerModel model);
}
