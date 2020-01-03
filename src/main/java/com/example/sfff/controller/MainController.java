package com.example.sfff.controller;

import com.example.sfff.domain.Product;
import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class MainController {
    @Autowired
    private ProductRepo productRepo;

//
//    @GetMapping("/")
//    public String greeting(Map<String, Object> model) {
//        return "greeting";
//    }

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);

        return "main";
    }

    @GetMapping("search")
    public String search(@RequestParam String search, Map<String, Object> model) {
        Iterable<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productRepo.findByTitleContaining(search);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);

        return "main";
    }
}
