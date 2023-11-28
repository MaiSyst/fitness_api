package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.RoomServices;
import com.maisyst.fitness.dao.services.UserService;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.AuthRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final RoomServices roomServices;

    public AuthController(UserService userService, RoomServices roomServices) {
        this.userService = userService;
        this.roomServices = roomServices;
    }

    @PostMapping("/checkToken")
    public ResponseEntity<Object> signIn(@RequestBody CheckTokenRequest token) {
        var data = userService.checkToken(token.token());
        if (data.getStatus() == HttpStatus.OK) {

            return new ResponseEntity<>(data.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(data.getMessage(), data.getStatus());
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody AuthRequest authRequest) {
        var response = userService.signIn(authRequest);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }

    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody @Validated AuthRequestCreated authRequest) {
        var roomModel = roomServices.findById(authRequest.roomId());
        if (roomModel.getStatus() == HttpStatus.OK) {
            var response = userService.add(new UserModel(
                    authRequest.username(),
                    authRequest.firstName(),
                    authRequest.lastName(),
                    authRequest.date(),
                    authRequest.address(),
                    authRequest.phoneNumber(),
                    roomModel.getData(),
                    authRequest.password(), true, AuthRole.USER));

            if (response.getStatus() == HttpStatus.OK) {
                roomModel.getData().setManager(response.getData());
                roomServices.update(roomModel.getData().getRoomId(), roomModel.getData());
                return new ResponseEntity<>("User added with success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getMessage(), response.getStatus());
            }
        } else {
            return new ResponseEntity<>(roomModel.getMessage(), roomModel.getStatus());
        }

    }

    @PostMapping("/updatePassword/{identityEMF}")
    public ResponseEntity<String> updatePassword(@RequestBody RequestSingle<String> request, @PathVariable String identityEMF) {
        var userModel = userService.updatePassword(identityEMF, request.arg());
        if (userModel.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Password updated", userModel.getStatus());
        } else {
            return new ResponseEntity<>(userModel.getMessage(), userModel.getStatus());
        }
    }

    @PutMapping("/update/{identityEMF}")
    public ResponseEntity<String> update(@RequestBody AuthRequestCreated model, @PathVariable String identityEMF) {
        var userModel = userService.findByUsername(identityEMF);
        if (userModel.getStatus() == HttpStatus.OK) {
            if (!model.roomId().equalsIgnoreCase("room available")){
                var result = userService.delete(Collections.singletonList(userModel.getData().getUserId()));
                if (result.getStatus() == HttpStatus.OK) {
                    var roomModel = roomServices.findById(model.roomId());
                    if (roomModel.getStatus() == HttpStatus.OK) {
                        var response = userService.addUpdate(new UserModel(
                                userModel.getData().getUsername(),
                                model.firstName(),
                                model.lastName(),
                                model.date(),
                                model.address(),
                                model.phoneNumber(),
                                roomModel.getData(),
                                userModel.getData().getPassword(), true, AuthRole.USER));

                        if (response.getStatus() == HttpStatus.OK) {
                            roomModel.getData().setManager(response.getData());
                            roomServices.update(roomModel.getData().getRoomId(), roomModel.getData());
                            return new ResponseEntity<>("User added with success", HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(response.getMessage(), response.getStatus());
                        }
                    } else {
                        return new ResponseEntity<>(roomModel.getMessage(), roomModel.getStatus());
                    }
                } else {
                    return new ResponseEntity<>(result.getMessage(), userModel.getStatus());
                }
            } else {
                UserModel users = new UserModel();
                users.setFirstName(model.firstName());
                users.setLastName(model.lastName());
                users.setAddress(model.address());
                users.setPhoneNumber(model.phoneNumber());
                users.setDate(model.date());
                users.setPassword(model.password());
                userService.update(identityEMF, users);
            }
            return new ResponseEntity<>("User updated", userModel.getStatus());
        } else {
            return new ResponseEntity<>(userModel.getMessage(), userModel.getStatus());
        }

    }

    @PostMapping("/disableOrEnableAccount/{username}")
    public ResponseEntity<String> disableOrEnableAccount(@PathVariable String username, @RequestBody AuthRequestDisableOrEnable auth) {
        var result = userService.disableOrEnableUsername(username, auth.isActive());
        if (result.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getMessage(), result.getStatus());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestBody DeleteManyRequest<String> ids) {
        var response = userService.delete(ids.ids());
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<Object> fetchAll() {
        var response = userService.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
}
