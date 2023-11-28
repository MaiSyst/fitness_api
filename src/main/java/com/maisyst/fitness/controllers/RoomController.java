package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.RoomServices;
import com.maisyst.fitness.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomServices roomServices;

    public RoomController(RoomServices roomServices) {
        this.roomServices = roomServices;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody RoomRequest roomRequest) {
            UserModel userModel=null;
            var response = roomServices.insert(new RoomModel(
                    roomRequest.roomName(),
                   userModel
            ));
            if (response.getStatus() == HttpStatus.OK) {
                var roomModel = response.getData();
                return new ResponseEntity<>(new RoomWithTotalSubscribeResponse(
                        roomModel.getRoomId(),
                        roomModel.getRoomName(),
                        0
                ), HttpStatus.OK);
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
            return new ResponseEntity<>(new ArrayList<>(), response.getStatus());
        }
    }

    @GetMapping("/fetchWithTotalSubscribe")
    public ResponseEntity<List<RoomWithTotalSubscribeResponse>> fetchRoomWithTotalSubscribeModel() {
        var response = roomServices.fetchRoomWithTotalSubscribeModel();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(new ArrayList<>(), response.getStatus());
        }
    }
    @GetMapping("/fetchRoomNoManager")
    public ResponseEntity<List<RoomNoManager>> fetchRoomNoManager() {
        var response = roomServices.findAllRoomNoManager();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(new ArrayList<>(), response.getStatus());
        }
    }

    @GetMapping("/fetchById/{room_id}")
    public ResponseEntity<RoomWithTotalSubscribeResponse> fetch(@PathVariable String room_id) {
        var response = roomServices.findRoomById(room_id);
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

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> delete(@PathVariable String roomId) {
        var roomResponse = roomServices.deleteById(roomId);
        if (roomResponse.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Room have been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(roomResponse.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
