package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.SubscriptionServices;
import com.maisyst.fitness.models.SubscriptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final SubscriptionServices subscriptionServices;

    public SubscriptionController(SubscriptionServices subscriptionServices) {
        this.subscriptionServices = subscriptionServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody SubscriptionModel model) {
        var response = subscriptionServices.insert(model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Subscription was added with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<SubscriptionModel>> fetchAll() {
        var response = subscriptionServices.fetchAll();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

}
