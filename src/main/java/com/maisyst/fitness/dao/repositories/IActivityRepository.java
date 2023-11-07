package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IActivityRepository extends JpaRepository<ActivityModel, String> {
    @Query(value = "select * from activity where label like concat('%',?1,'%')",nativeQuery = true)
    List<ActivityModel> searchIt(String query);
}
