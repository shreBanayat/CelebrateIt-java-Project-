package com.celebrateit.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.celebrateit.pojo.Users;


public interface UserDao extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    
    //BYID
   // Optional<Users> findByUserId(String userId);

    //boolean existsByEmail(String email);
}
