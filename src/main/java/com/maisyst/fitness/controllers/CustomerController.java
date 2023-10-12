package com.maisyst.fitness.controllers;

import com.maisyst.fitness.dao.services.CustomerServices;
import com.maisyst.fitness.models.CustomerModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerServices customerServices;
    public CustomerController(CustomerServices customerServices){
        this.customerServices=customerServices;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(CustomerModel model){
        var response=customerServices.insert(model);
       if(response.getStatus()==HttpStatus.OK) {
           return new ResponseEntity<>("Customer was added with Success", HttpStatus.OK);
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
