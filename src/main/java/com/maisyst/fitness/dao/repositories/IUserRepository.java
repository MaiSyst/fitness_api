package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUsernameAndIsActive(String username,boolean isActive);
    Optional<UserModel> findByUsername(String username);

}
