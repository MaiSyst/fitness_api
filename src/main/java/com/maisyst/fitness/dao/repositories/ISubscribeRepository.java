package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscribeRepository extends JpaRepository<SubscribeModel, String> {
    List<SubscribeModel>findAllByCustomer(CustomerModel customer);
    List<SubscribeModel>findAllByCustomerAndIsActive(CustomerModel customer,boolean isActive);
}
