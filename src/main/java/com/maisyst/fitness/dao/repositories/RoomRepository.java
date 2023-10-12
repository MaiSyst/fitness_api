package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel,String> {}
