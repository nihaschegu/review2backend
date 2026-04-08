package com.traveleasy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.traveleasy.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}