package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.model.User;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    @Autowired
    private UserRepository userRepository;

    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    public String shortenUrl(String originalUrl, String username, String title) {

        try {
            new URI(originalUrl).toURL();
        } catch (Exception e) {
            throw new RuntimeException("Invalid URL");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(generateShortCode());
        url.setUser(user);
        url.setClickCount(0);
        url.setTitle(title != null && !title.isEmpty() ? title : "Untitled");

        repository.save(url);

        return "http://localhost:8082/" + url.getShortCode();
    }

    public String getOriginalUrl(String shortCode) {
        Url url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);

        return url.getOriginalUrl();
    }

    public List<Url> getUserUrls(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findAll()
                .stream()
                .filter(url -> url.getUser() != null &&
                        url.getUser().getId().equals(user.getId()))
                .toList();
    }

    public void deleteUrl(Long id) {
        repository.deleteById(id);
    }
}