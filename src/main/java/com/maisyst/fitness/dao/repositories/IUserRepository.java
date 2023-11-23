package com.maisyst.fitness.dao.repositories;

import com.maisyst.fitness.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUsernameAndIsActive(String username,boolean isActive);
    Optional<UserModel> findByUsername(String username);

}
