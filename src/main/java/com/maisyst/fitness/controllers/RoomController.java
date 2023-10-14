package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.RoomServices;
import com.maisyst.fitness.models.RoomModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomServices roomServices;

    public RoomController(RoomServices roomServices) {
        this.roomServices = roomServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody RoomModel model) {
        var response = roomServices.insert(model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Room was added with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<RoomModel>> fetchAll() {
        var response = roomServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

     @GetMapping("/fetchById/{room_id}")
    public ResponseEntity<RoomModel> fetch(@PathVariable String room_id) {
        var response = roomServices.findById(room_id);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

    @PutMapping("/update/{room_id}")
    public ResponseEntity<String> update(@PathVariable String room_id, @RequestBody RoomModel model) {
        var roomResponse = roomServices.update(room_id, model);
        if (roomResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Room have been updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(roomResponse.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
     @DeleteMapping("/delete/{room_id}")
    public ResponseEntity<String> delete(@PathVariable String room_id) {
        var roomResponse = roomServices.deleteById(room_id);
        if (roomResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Room have been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(roomResponse.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
