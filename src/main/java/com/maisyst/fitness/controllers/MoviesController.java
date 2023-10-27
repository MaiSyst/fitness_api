package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.MoviesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {
    private final MoviesServices moviesServices;
    @Autowired
    public MoviesController(MoviesServices moviesServices) {
        this.moviesServices = moviesServices;
    }

    @GetMapping("/fetch")
    public ResponseEntity<Object> fetchAll(){
        var response=moviesServices.fetchAll();
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response.getMessage(),response.getStatus());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String>addMovies(@RequestParam("file") MultipartFile file){
    return new ResponseEntity<>("Movies",HttpStatus.OK);
    }
}
