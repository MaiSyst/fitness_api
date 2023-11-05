package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.models.PlanningResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPlanningRepository extends JpaRepository<PlanningModel, UUID> {
    @Query(value = "select * from planning where day like concat('%',?1,'%')",nativeQuery = true)
    List<PlanningModel> searchIt(String query);
}
