package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerModel, String> {
    Optional<CustomerModel>findByIdentityEMF(String identity);
    List<CustomerModel> findAllByRoom(RoomModel room);
}
