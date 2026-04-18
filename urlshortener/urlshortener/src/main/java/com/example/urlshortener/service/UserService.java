package com.example.urlshortener.service;

import com.example.urlshortener.model.User;
import com.example.urlshortener.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // 📝 Register new user
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return repository.save(user);
    }

    // 🔑 Login user
    public User login(String username, String password) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}