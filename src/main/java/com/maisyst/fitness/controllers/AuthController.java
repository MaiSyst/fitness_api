package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.UserService;
import com.maisyst.fitness.models.AuthRequest;
import com.maisyst.fitness.models.AuthResponse;
import com.maisyst.fitness.models.AuthRole;
import com.maisyst.fitness.models.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/signIn")
    public ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Hello API", HttpStatus.OK);
    }
    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody AuthRequest authRequest) {
        var response=userService.signIn(authRequest);
        if(response.getStatus()==HttpStatus.OK){
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }

    }

    @PostMapping("/signOut")
    public ResponseEntity<AuthResponse> signOut(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(new AuthResponse("Aboubacar", "12290hhhdj", AuthRole.CUSTOMER), HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody @Validated AuthRequest authRequest) {
        var response = userService.add(new UserModel(
                authRequest.username(),
                authRequest.password(), AuthRole.USER,true));
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("User added with success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @DeleteMapping("/remove/{user_id}")
    public ResponseEntity<String> removeUser(@PathVariable String user_id) {
        var response = userService.delete(user_id);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
}
