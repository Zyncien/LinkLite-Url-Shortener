package com.example.urlshortener.controller;

import com.example.urlshortener.model.User;
import com.example.urlshortener.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService service;

    // 📝 Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user.getUsername(), user.getPassword());
    }

    // 🔑 Login
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return service.login(user.getUsername(), user.getPassword());
    }
}