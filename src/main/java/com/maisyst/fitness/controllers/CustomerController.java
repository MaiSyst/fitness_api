package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.CustomerServices;
import com.maisyst.fitness.utils.TypeSubscription;
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

    @PostMapping("/add/{subscription_type}/{activity_id}")
    public ResponseEntity<String> add(@PathVariable String subscription_type,@PathVariable int activity_id,@RequestBody CustomerModel model){
        var response=customerServices.insertWithSubscription(subscription_type,activity_id,model);
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @PutMapping("/update/{activity_id}")
    public ResponseEntity<String> update(@PathVariable int activity_id,@RequestBody CustomerModel model){
        var response=customerServices.update(activity_id,model);
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
       }else{
           return new ResponseEntity<>(response.getMessage(),response.getStatus());
       }
    }
    @DeleteMapping("/delete/{activity_id}")
    public ResponseEntity<String> delete(@PathVariable int activity_id){
        var response=customerServices.deleteById(activity_id);
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
}
