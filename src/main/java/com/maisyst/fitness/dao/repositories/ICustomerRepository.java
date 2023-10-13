package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerModel,Integer> {}
