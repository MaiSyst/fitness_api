package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.utils.TypeSubscription;
import com.maisyst.fitness.models.SubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubscriptionRepository extends JpaRepository<SubscriptionModel,String> {
    @Query("SELECT s FROM subscription s WHERE s.type = ?1")
    Optional<SubscriptionModel> findByType(TypeSubscription type);
}
