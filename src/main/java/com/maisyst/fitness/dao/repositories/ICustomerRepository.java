package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerModel, String> {
    Optional<CustomerModel>findByUsername(String username);
}
