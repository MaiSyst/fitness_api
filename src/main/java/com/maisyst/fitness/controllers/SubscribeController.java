package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.SubscribeServices;
import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {
    private final SubscribeServices subscribeServices;
    public SubscribeController(SubscribeServices subscribeServices){
        this.subscribeServices=subscribeServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(SubscribeModel model){
         var response=subscribeServices.insert(model);
       if(response.getStatus()== HttpStatus.OK) {
           return new ResponseEntity<>("Subscribe was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
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
}
