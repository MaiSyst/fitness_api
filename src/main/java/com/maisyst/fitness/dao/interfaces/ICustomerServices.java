package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.TypeSubscription;
import com.maisyst.fitness.models.CustomerModel;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,Integer>{
    MaiResponse<CustomerModel> insertWithSubscription(TypeSubscription typeSubscription,int activity_id, CustomerModel model);
}
