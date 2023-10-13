package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.RoomServices;
import com.maisyst.fitness.models.RoomModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomServices roomServices;

    public RoomController(RoomServices roomServices) {
        this.roomServices = roomServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(RoomModel model) {
        var response = roomServices.insert(model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Room was added with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/api/fetchAll")
    public ResponseEntity<List<RoomModel>> fetchAll() {
        var response = roomServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }
}
