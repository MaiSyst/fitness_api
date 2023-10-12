package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.models.CoachModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachModel,Integer> {}
