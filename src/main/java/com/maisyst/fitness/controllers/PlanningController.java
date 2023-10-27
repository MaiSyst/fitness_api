package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.PlanningServices;
import com.maisyst.fitness.models.PlanningModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {
    private final PlanningServices planningServices;
    public PlanningController(PlanningServices planningServices){
        this.planningServices=planningServices;
    }

    @PostMapping("/add/{activity_id}/{room_id}")
    public ResponseEntity<String> add(@PathVariable UUID activity_id, @PathVariable String room_id, @RequestBody PlanningModel model){
         var response=planningServices.insert(activity_id,room_id,model);
       if(response.getStatus()== HttpStatus.OK) {
           return new ResponseEntity<>("Planning was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @GetMapping("/fetchAll")
    public ResponseEntity<List<PlanningModel>> fetchAll(){
        var response=planningServices.findAllWithActivityAndRoom();
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(new ArrayList<>(),response.getStatus());
        }
    }
}
