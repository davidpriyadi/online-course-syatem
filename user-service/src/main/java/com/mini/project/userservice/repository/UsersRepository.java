package com.mini.project.userservice.repository;

import com.mini.project.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    boolean existsUsersByEmail(String email);
    Optional<Users> findByEmail(String email);
}