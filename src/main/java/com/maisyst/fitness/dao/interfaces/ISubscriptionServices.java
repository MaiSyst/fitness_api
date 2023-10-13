package com.maisyst.fitness.dao.interfaces;

import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.utils.TypeSubscription;
import com.maisyst.fitness.models.SubscriptionModel;

public non-sealed interface ISubscriptionServices extends IServices<SubscriptionModel,String>{
    MaiResponse<SubscriptionModel> findByType(TypeSubscription typeSubscription);
}
