package com.micro.service.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.service.app.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
