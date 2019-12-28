package com.example.sfff.controller;

import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public String product(Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);

        return "product";
    }

    @PostMapping
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            Map<String, Object> model) {
        Product product = new Product(title, description, user);

        productRepo.save(product);

        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);

        return "product";
    }
}
