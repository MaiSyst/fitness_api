package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.PlanningModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlanningRepository extends JpaRepository<PlanningModel,Integer> {}
