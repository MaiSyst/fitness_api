package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.CustomerServices;
import com.maisyst.fitness.models.CustomerSubscribeResponse;
import com.maisyst.fitness.models.DeleteManyRequest;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.RequestSingle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerServices customerServices;

    public CustomerController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @PostMapping("/add/{subscriptionType}/{activityId}/{roomId}")
    public ResponseEntity<Object> add(@PathVariable String subscriptionType, @PathVariable String activityId, @PathVariable String roomId, @RequestBody CustomerModel model) {
        var response = customerServices.insertWithSubscription(subscriptionType, activityId, roomId, model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @PutMapping("/update/{roomId}/{customId}")
    public ResponseEntity<String> update(@PathVariable String customId, @PathVariable String roomId, @RequestBody CustomerModel model) {
        if (roomId.equals("user")) {
            var response = customerServices.update(customId, model);
            if (response.getStatus() == HttpStatus.OK) {
                return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getMessage(), response.getStatus());
            }

        }
        var response = customerServices.update(customId, roomId, model);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }
  @PutMapping("/updateSubscription/{identify}")
    public ResponseEntity<String> updateSubscription(@PathVariable String identify,@RequestBody RequestSingle<String> subscriptionId) {
            var response = customerServices.resubscribe(identify,subscriptionId.arg());
            if (response.getStatus() == HttpStatus.OK) {
                return new ResponseEntity<>("Subscription was been updated with Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getMessage(), response.getStatus());
            }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteManyRequest<String> requestToDeleteMany) {
        var response = customerServices.deleteMany(requestToDeleteMany.ids());
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>("Customer have been deleted with Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), response.getStatus());
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerSubscribeResponse>> fetchAll() {
        var response = customerServices.fetchAllWithSubscribes();
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

    @GetMapping("/fetchAllByRoom/{roomId}")
    public ResponseEntity<List<CustomerSubscribeResponse>> fetchAllByRoom(@PathVariable String roomId) {
        var response = customerServices.fetchAllByRoom(roomId);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }

    @GetMapping("/checkCustomer/{roomId}/{identityEmf}")
    public ResponseEntity<CustomerSubscribeResponse> checkCustomer(@PathVariable String roomId, @PathVariable String identityEmf) {
        var response = customerServices.findByIdentityEMFAndRoom(roomId, identityEmf);
        if (response.getStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getData(), response.getStatus());
        } else {
            return new ResponseEntity<>(null, response.getStatus());
        }
    }
}
