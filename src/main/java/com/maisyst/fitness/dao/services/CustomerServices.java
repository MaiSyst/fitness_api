package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.interfaces.ICustomerServices;
import com.maisyst.fitness.dao.repositories.CoachRepository;
import com.maisyst.fitness.dao.repositories.CustomerRepository;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices implements ICustomerServices {
    private final CustomerRepository customerRepository;
    public CustomerServices(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    public MaiResponse<CustomerModel> insert(CustomerModel model) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> findById(Integer id) {
        return null;
    }

    @Override
    public MaiResponse<List<CustomerModel>> fetchAll() {
        return null;
    }

    @Override
    public MaiResponse<String> insertMany(List<CustomerModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> update(Integer id, CustomerModel model) {
        return null;
    }
}
