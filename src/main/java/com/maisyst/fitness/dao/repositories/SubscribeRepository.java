package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeModel,Integer> {}
