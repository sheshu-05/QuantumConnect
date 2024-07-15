package com.QC.QuantumConnect.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QC.QuantumConnect.entities.User;

public interface UserRepo extends JpaRepository<User, String>{
    //custom methods
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailToken(String id);
} 
