package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.SubscribeServices;
import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {
    private final SubscribeServices subscribeServices;
    public SubscribeController(SubscribeServices subscribeServices){
        this.subscribeServices=subscribeServices;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<SubscribeModel>> fetchAll(){
        var response=subscribeServices.findAllWithSubscriptionAndCustomer();
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
    @GetMapping("/fetchById/{subscribe_id}")
    public ResponseEntity<SubscribeModel> fetchById(@PathVariable int subscribe_id){
        var response=subscribeServices.findById(subscribe_id);
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
}
