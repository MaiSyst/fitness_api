package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.MoviesModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMoviesRepository extends JpaRepository<MoviesModels,Integer> {
}
