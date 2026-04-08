package com.traveleasy.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveleasy.backend.model.User;
import com.traveleasy.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // ✅ REGISTER USER
    public User registerUser(User user) {
        // Check if email already exists
        User existing = repo.findByEmail(user.getEmail());

        if (existing != null) {
            throw new RuntimeException("User already exists with this email");
        }

        // Save new user
        return repo.save(user);
    }

    // ✅ LOGIN USER
    public User loginUser(String email, String password) {
        User existing = repo.findByEmail(email);

        if (existing == null) {
            throw new RuntimeException("User not found");
        }

        if (!existing.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return existing;
    }
}