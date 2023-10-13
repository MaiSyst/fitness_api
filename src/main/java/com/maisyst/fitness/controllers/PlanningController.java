package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.PlanningServices;
import com.maisyst.fitness.models.PlanningModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {
    private final PlanningServices planningServices;
    public PlanningController(PlanningServices planningServices){
        this.planningServices=planningServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(PlanningModel model){
         var response=planningServices.insert(model);
       if(response.getStatus()== HttpStatus.OK) {
           return new ResponseEntity<>("Planning was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @GetMapping("/fetchAll")
    public ResponseEntity<List<PlanningModel>> fetchAll(){
        var response=planningServices.fetchAll();
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
}
