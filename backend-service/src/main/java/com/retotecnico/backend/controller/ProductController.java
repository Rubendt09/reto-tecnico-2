package com.retotecnico.backend.controller;

import com.retotecnico.backend.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        return Arrays.asList(
            new Product(1, "Title 01"),
            new Product(2, "Title 02")
        );
    }
}
