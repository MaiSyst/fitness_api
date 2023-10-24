package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.MoviesModels;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;

public interface IMoviesServices {
    MaiResponse<List<MoviesModels>> fetchAll();

}
