package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.CustomerSubscribeResponse;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;

import java.util.List;

public non-sealed interface ICustomerServices extends IServices<CustomerModel,String>{
    MaiResponse<CustomerSubscribeResponse> insertWithSubscription(String typeSubscription, String activity_id,String roomId, CustomerModel model);
    MaiResponse<CustomerModel> update(String customerId, String subscriptionId,String roomId, CustomerModel model);
    MaiResponse<CustomerSubscribeResponse> findByIdentityEMFAndRoom(String roomId, String identity);
    MaiResponse<List<CustomerSubscribeResponse>> fetchAllByRoom(String roomId);
    MaiResponse<CustomerModel> resubscribe(String customerId,String subscriptionType);
    MaiResponse<List<CustomerSubscribeResponse>> fetchAllWithSubscribes();
}
