package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.CustomerServices;
import com.maisyst.fitness.models.DeleteManyRequest;
import com.maisyst.fitness.models.CustomerModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerServices customerServices;
    public CustomerController(CustomerServices customerServices){
        this.customerServices=customerServices;
    }

    @PostMapping("/add/{subscriptionType}/{activityId}/{roomId}")
    public ResponseEntity<String> add(@PathVariable String subscriptionType, @PathVariable String activityId,@PathVariable String roomId, @RequestBody CustomerModel model){
        System.out.println(CustomerController.class.getName()+" ROOMID="+roomId);
        var response=customerServices.insertWithSubscription(subscriptionType,activityId,roomId,model);
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @PutMapping("/update/{subscriptionType}/{activityId}/{customId}")
    public ResponseEntity<String> update(@PathVariable String subscriptionType,@PathVariable String activityId,@PathVariable String customId,@RequestBody CustomerModel model){
        var response=customerServices.update(activityId,subscriptionType,customId,model);
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteManyRequest<String> requestToDeleteMany){
        var response=customerServices.deleteMany(requestToDeleteMany.ids());
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer have been deleted with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerModel>> fetchAll(){
        var response=customerServices.fetchAll();
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
    @GetMapping("/fetchAllByRoom/{roomId}")
    public ResponseEntity<List<CustomerModel>> fetchAllByRoom(@PathVariable String roomId){
        var response=customerServices.fetchAllByRoom(roomId);
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
    @GetMapping("/checkCustomer/{identityEmf}")
    public ResponseEntity<CustomerModel> checkCustomer(@PathVariable String identityEmf){
        var response=customerServices.findByIdentityEMF(identityEmf);
        if(response.getStatus()== HttpStatus.OK){
            return new ResponseEntity<>(response.getData(),response.getStatus());
        }else{
            return new ResponseEntity<>(null,response.getStatus());
        }
    }
}
