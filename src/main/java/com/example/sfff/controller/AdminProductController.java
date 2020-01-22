package com.example.sfff.controller;

import com.example.sfff.domain.Category;
import com.example.sfff.domain.Product;
import com.example.sfff.repos.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminProductController {
    @Autowired
    private ProductRepo productRepo;


    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String product(Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAll();

        model.put("products", products);
        model.put("categories",Category.values());
        return "productList";
    }
    @GetMapping("/delete")
    public String delete(
            @RequestParam int productId
    ){
        Product product = productRepo.findById(productId);
        productRepo.delete(product);
        return "redirect:/admin/product";
    }


    @PostMapping
    public String add(
            @RequestParam Category category,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int price,
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Product product = new Product(category, title, description, price);

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

        return "productList";
    }




}
