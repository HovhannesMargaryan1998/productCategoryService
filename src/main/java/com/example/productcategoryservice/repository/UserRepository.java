package com.example.productcategoryservice.repository;

import com.example.productcategoryservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User>findByEmail(String email);
}
