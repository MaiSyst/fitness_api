package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<ActivityModel, String> {
    @Query(value = "select * from activity where label like concat('%',?1,'%')",nativeQuery = true)
    List<ActivityModel> searchIt(String query);
}
