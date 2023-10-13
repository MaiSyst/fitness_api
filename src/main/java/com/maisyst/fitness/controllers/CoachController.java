package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.ActivityServices;
import com.maisyst.fitness.dao.services.CoachServices;
import com.maisyst.fitness.models.CoachModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coach")
public class CoachController {
    private final CoachServices coachServices;
    private final ActivityServices activityServices;

    public CoachController(CoachServices coachServices, ActivityServices activityServices) {
        this.coachServices = coachServices;
        this.activityServices = activityServices;
    }

    @PostMapping("/add/{activity_id}")
    public ResponseEntity<String> add(@PathVariable int activity_id, @RequestBody CoachModel model) {
        var activityResponse = activityServices.findById(activity_id);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            model.setActivityCoach(activityResponse.getData());
            model.setSpeciality(activityResponse.getData().getLabel());
            var response = coachServices.insert(model);
            if (response.getStatus() == HttpStatus.OK) {
                return new ResponseEntity<>("Coach was added with Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getMessage(), response.getStatus());
            }
        }else{
            return new ResponseEntity<>("Error Activity= "+activityResponse.getMessage(), activityResponse.getStatus());
        }
    }

    @PutMapping("/update/{activity_id}/{coach_id}")
    public ResponseEntity<String> update(@PathVariable int activity_id,@PathVariable int coach_id, @RequestBody CoachModel model) {

        var activityResponse = activityServices.findById(activity_id);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            var data = activityResponse.getData();
            model.setActivityCoach(data);
            model.setSpeciality(data.getLabel());
            System.out.println(model.getSpeciality());
            var response = coachServices.update(coach_id, model);
            if (response.getStatus() == HttpStatus.OK) {
                return new ResponseEntity<>("Coach was added with Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getMessage(), response.getStatus());
            }
        }else{
            return new ResponseEntity<>("Error Activity= "+activityResponse.getMessage(), activityResponse.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<CoachModel>> fetchAll() {
        var response = coachServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            System.out.println(response.getData());
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }
    @DeleteMapping("/delete/{coach_id}")
    public ResponseEntity<String> deleteById(@PathVariable int coach_id) {
        var response = coachServices.deleteById(coach_id);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
}
