package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISubscribeRepository extends JpaRepository<SubscribeModel, String> { }
