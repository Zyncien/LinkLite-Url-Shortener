package com.example.urlshortener.controller;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UrlController {

    @Autowired
    private UrlService service;

    @GetMapping("/shorten")
    public String shorten(
            @RequestParam String url,
            @RequestParam String username,
            @RequestParam(required = false) String title
    ) {
        return service.shortenUrl(url, username, title);
    }

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
    }

    @GetMapping("/all")
    public List<Url> getUserUrls(@RequestParam String username) {
        return service.getUserUrls(username);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUrl(id);
    }
}