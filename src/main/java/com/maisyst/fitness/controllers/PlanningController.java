package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.PlanningServices;
import com.maisyst.fitness.models.DeleteManyRequest;
import com.maisyst.fitness.models.PlanningModel;
import com.maisyst.fitness.models.PlanningResponse;
import jakarta.websocket.server.PathParam;
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

    public PlanningController(PlanningServices planningServices) {
        this.planningServices = planningServices;
    }

    @PostMapping("/add/{activity_id}/{room_id}")
    public ResponseEntity<String> add(@PathVariable String activity_id, @PathVariable String room_id, @RequestBody PlanningModel model) {
        var response = planningServices.insert(UUID.fromString(activity_id), room_id, model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Planning was added with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
    @PutMapping("/update/{activityId}/{roomId}/{planningId}")
    public ResponseEntity<String> update(@PathVariable String activityId, @PathVariable String roomId,@PathVariable String planningId, @RequestBody PlanningModel model) {
        var response = planningServices.update(UUID.fromString(activityId), roomId,UUID.fromString(planningId), model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Planning was updated with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/findAllWithActivityAndRoom")
    public ResponseEntity<List<PlanningModel>> findAllWithActivityAndRoom() {
        var response = planningServices.findAllWithActivityAndRoom();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(new ArrayList<>(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<PlanningResponse>> fetchAll() {
        var response = planningServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(new ArrayList<>(), response.getStatus());
        }
    }

    @DeleteMapping("/delete/{planningId}")
    public ResponseEntity<String> deletePlanning(@PathVariable String planningId) {
        var response = planningServices.deleteById(UUID.fromString(planningId));
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Planning deleted", response.getStatus());
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteManyPlanning(@RequestBody DeleteManyRequest<String>planningsId) {
        System.out.println(planningsId.ids());
        var convertToUUID=planningsId.ids().stream().map(UUID::fromString).toList();
        var response = planningServices.deleteMany(convertToUUID);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Planning deleted", response.getStatus());
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
}
