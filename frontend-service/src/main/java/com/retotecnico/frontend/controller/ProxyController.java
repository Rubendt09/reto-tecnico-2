package com.retotecnico.frontend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ProxyController {

    @Value("${backend.service.url:http://backend-service:8080}")
    private String backendUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/products")
    public ResponseEntity<String> getProducts() {
        try {
            String url = backendUrl + "/api/products";
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("[{\"productId\": 0, \"title\": \"Error: " + e.getMessage() + "\"}]");
        }
    }
}
