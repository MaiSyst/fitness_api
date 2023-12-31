package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;

public sealed interface IServices<T,TID> permits IActivityServices, ICoachServices,
        ICustomerServices,
        ISubscriptionServices,
        IRoomServices {
    MaiResponse<T> insert(T model);

    MaiResponse<String> deleteById(TID id);
    MaiResponse<T> findById(TID id);

    MaiResponse<List<T>> fetchAll();

    MaiResponse<String> deleteMany(List<TID> ids);
    MaiResponse<T> update(TID id, T model);
}
