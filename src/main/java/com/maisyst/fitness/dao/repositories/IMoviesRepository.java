package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.MoviesModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMoviesRepository extends JpaRepository<MoviesModels, UUID> {
}
