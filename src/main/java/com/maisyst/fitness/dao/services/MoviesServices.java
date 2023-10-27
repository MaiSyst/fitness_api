package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.repositories.IMoviesRepository;
import com.maisyst.fitness.dao.services.interfaces.IMoviesServices;
import com.maisyst.fitness.models.MoviesModels;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesServices implements IMoviesServices {
    private final IMoviesRepository moviesRepository;
    private final StorageService storageService;
    @Autowired
    public MoviesServices(IMoviesRepository moviesRepository, StorageService storageService) {
        this.moviesRepository = moviesRepository;
        this.storageService = storageService;
    }

    @Override
    public MaiResponse<List<MoviesModels>> fetchAll() {
       try {
           return new MaiResponse.MaiSuccess<>(moviesRepository.findAll(), HttpStatus.OK);
       }catch(Exception ex){
             return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.NOT_FOUND);
       }
    }
}
