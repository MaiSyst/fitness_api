package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.SubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionModel,String> {}
