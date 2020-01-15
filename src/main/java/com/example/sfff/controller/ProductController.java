package com.example.sfff.controller;

import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String product(Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);

        return "product";
    }


    @PostMapping
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String category,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int price,
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Product product = new Product(category, title, description, price, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

           product.setFilename(resultFilename);
        }

        productRepo.save(product);

        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);

        return "product";
    }

    @GetMapping("/{cartProductId}")
    public String delete(
            @PathVariable Long cartProductId
    ){


        return "redirect:/admin/product";
    }


}
