package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.ActivityServices;
import com.maisyst.fitness.dao.services.CoachServices;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.DeleteManyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<String> add(@PathVariable String activity_id, @RequestBody CoachModel model) {
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
    public ResponseEntity<String> update(@PathVariable String activity_id,@PathVariable String coach_id, @RequestBody CoachModel model) {

        var activityResponse = activityServices.findById(activity_id);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            var data = activityResponse.getData();
            model.setActivityCoach(data);
            model.setSpeciality(data.getLabel());
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
    public ResponseEntity<Object> fetchAll() {
        var response = coachServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteById(@RequestBody DeleteManyRequest<String> coachIds) {
        var response = coachServices.deleteMany(coachIds.ids());
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
}
