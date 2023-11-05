package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.SubscriptionServices;
import com.maisyst.fitness.models.SubscriptionModel;
import com.maisyst.fitness.utils.MaiUID;
import com.maisyst.fitness.utils.TypeSubscription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.maisyst.fitness.utils.MaiUtils.getPriceSubscription;

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
    public ResponseEntity<List<Map<String, String>>> fetchAll() {
        var response = subscriptionServices.fetchAll();
        System.out.println(response.getData());
        if (response.getStatus() == HttpStatus.OK) {
            Map<String, String> data = new HashMap<>();
            List<Map<String, String>> responseData = new ArrayList<>();
            Executors.newSingleThreadExecutor()
                    .execute(() -> {
                        response.getData().forEach(x -> {
                            Map<String, String> map = new HashMap<>();
                            map.put("subscriptionId", String.valueOf(x.getSubscriptionId()));
                            map.put("label", x.getLabel());
                            map.put("price", String.valueOf(x.getPrice()));
                            map.put("type", x.getType().toString());
                            responseData.add(map);
                        });
                    });
            return new ResponseEntity<>(responseData, response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

    @GetMapping("/fetchById/{subscription_id}")
    public ResponseEntity<SubscriptionModel> fetchById(@PathVariable String subscription_id) {
        var response = subscriptionServices.findById(subscription_id);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

}
