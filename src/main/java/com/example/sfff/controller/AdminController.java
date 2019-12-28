package com.example.sfff.controller;

import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public String admin(Map<String, Object> model) {

        return "admin";
    }

}
