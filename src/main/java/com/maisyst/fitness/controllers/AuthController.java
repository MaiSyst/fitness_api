package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.UserService;
import com.maisyst.fitness.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/checkToken")
    public ResponseEntity<Object> signIn(@RequestBody CheckTokenRequest token) {
        var data=userService.checkToken(token.token());
        if(data.getStatus()==HttpStatus.OK) {

            return new ResponseEntity<>(data.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(data.getMessage(), data.getStatus());
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody AuthRequest authRequest) {
        var response = userService.signIn(authRequest);
        if (response.getStatus() == HttpStatus.OK) {
            System.out.println(response.getData());
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @PostMapping("/signInCustomer")
    public ResponseEntity<Object> signIn(@RequestBody AuthRequestCustomer authRequest) {
        var response = userService.signInCustomer(authRequest);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @PostMapping("/signOut")
    public ResponseEntity<Object> signOut(@RequestBody @Validated AuthRequest authRequest) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody @Validated AuthRequestCreated authRequest) {
        var response = userService.add(new UserModel(
                authRequest.username(),
                authRequest.password(), authRequest.role(), true));
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
