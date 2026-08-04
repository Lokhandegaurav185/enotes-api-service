package com.code.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
