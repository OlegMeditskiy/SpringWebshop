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

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Product> products;

        if (filter != null && !filter.isEmpty()) {
            products = productRepo.findByTitle(filter);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);

        return "main";
    }
}
