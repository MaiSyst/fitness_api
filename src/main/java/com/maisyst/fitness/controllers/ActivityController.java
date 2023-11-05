package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.ActivityServices;
import com.maisyst.fitness.dao.services.CoachServices;
import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.models.DeleteManyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityServices activityServices;
    private final CoachServices coachServices;

    public ActivityController(ActivityServices activityServices, CoachServices coachServices) {
        this.activityServices = activityServices;
        this.coachServices = coachServices;
    }


    @PostMapping("/add/{subscription_type}")
    public ResponseEntity<String> add(@PathVariable String subscription_type,@RequestBody ActivityModel model) {
        var response = activityServices.insertWithSubscription(subscription_type,model);

        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Activity was added.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
     @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody ActivityModel model) {
        var response = activityServices.insert(model);

        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Activity was added.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<Object> fetchAll() {
        var response = activityServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authorized", response.getStatus());
        }

    }

    @PutMapping("/update/{activity_id}")
    public ResponseEntity<String> update(@PathVariable UUID activity_id, @RequestBody ActivityModel model) {
        var activityResponse = activityServices.update(activity_id, model);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            Executors.newSingleThreadExecutor().submit(() -> activityResponse.getData().getCoach().forEach(coachModel -> {
                coachModel.setSpeciality(activityResponse.getData().getLabel());
                coachServices.update(coachModel.getCoachId(), coachModel);
            }));

            return new ResponseEntity<>("Activity updated", activityResponse.getStatus());
        } else {
            return new ResponseEntity<>(activityResponse.getMessage(), activityResponse.getStatus());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody UUID activityId) {
        var activityResponse = activityServices.deleteById(activityId);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(activityResponse.getData(), activityResponse.getStatus());
        } else {
            return new ResponseEntity<>(activityResponse.getMessage(), activityResponse.getStatus());
        }
    }
    @PostMapping("/deleteMany")
    public ResponseEntity<Object> delete(@RequestBody DeleteManyRequest<String> requestToDeleteMany) {
        var activitiesUUID=new ArrayList<UUID>();
        requestToDeleteMany.ids().forEach(act->activitiesUUID.add(UUID.fromString(act)));
        var activityResponse = activityServices.deleteMany(activitiesUUID);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(activityResponse.getData(), activityResponse.getStatus());
        } else {
            return new ResponseEntity<>(activityResponse.getMessage(), activityResponse.getStatus());
        }
    }
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Object> searchIt(@RequestParam String query) {
        var activityResponse = activityServices.searchIt(query);
        if (activityResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(activityResponse.getData(), activityResponse.getStatus());
        } else {
            return new ResponseEntity<>(activityResponse.getMessage(), activityResponse.getStatus());
        }
    }
}
