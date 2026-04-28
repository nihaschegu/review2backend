package com.traveleasy.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.traveleasy.backend.model.User;
import com.traveleasy.backend.repository.UserRepository;
import com.traveleasy.backend.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTER USER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and Password required");
        }

        User savedUser = repo.save(user);

        // ❌ DO NOT return password
        savedUser.setPassword(null);

        return ResponseEntity.ok(savedUser);
    }

    // ✅ LOGIN + JWT TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and Password required");
        }

        User existing = repo.findByEmail(user.getEmail());

        if (existing != null && existing.getPassword().equals(user.getPassword())) {

            // ✅ generate token
            String token = jwtUtil.generateToken(existing.getEmail());

            // ❌ remove password before sending
            existing.setPassword(null);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", existing);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid credentials ❌");
    }

    // ✅ GET USER COUNT (ADMIN)
    @GetMapping("/count")
    public ResponseEntity<?> getUserCount() {
        long count = repo.count();
        return ResponseEntity.ok(count);
    }
}