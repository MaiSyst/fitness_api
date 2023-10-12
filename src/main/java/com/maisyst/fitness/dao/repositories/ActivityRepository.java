package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel,Integer> {}
