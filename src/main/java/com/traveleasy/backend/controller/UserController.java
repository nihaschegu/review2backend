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
@CrossOrigin(origins = "*") // ✅ allow all (change later for security)
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            // ✅ validation
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and Password required ❗");
            }

            // ✅ check duplicate email
            User existing = repo.findByEmail(user.getEmail());
            if (existing != null) {
                return ResponseEntity.badRequest().body("Email already registered ❌");
            }

            // ✅ save user
            User savedUser = repo.save(user);

            // ❌ remove password before sending
            savedUser.setPassword(null);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and Password required ❗");
            }

            User existing = repo.findByEmail(user.getEmail());

            if (existing != null && existing.getPassword().equals(user.getPassword())) {

                // ✅ generate JWT token
                String token = jwtUtil.generateToken(existing.getEmail());

                // ❌ remove password before sending
                existing.setPassword(null);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", existing);

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(401).body("Invalid credentials ❌");

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Login failed: " + e.getMessage());
        }
    }

    // ================= USER COUNT =================
    @GetMapping("/count")
    public ResponseEntity<?> getUserCount() {
        try {
            long count = repo.count();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error fetching user count: " + e.getMessage());
        }
    }
}