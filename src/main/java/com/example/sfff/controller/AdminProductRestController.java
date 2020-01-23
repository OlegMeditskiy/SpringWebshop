package com.example.sfff.controller;

import com.example.sfff.domain.Category;
import com.example.sfff.domain.Product;
import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminProductRestController {
    @Autowired
    private ProductRepo productRepo;

    @Value("${upload.path}")
    private String uploadPath;



    @PostMapping("/addProduct/{category}/{title}/{description}/{price}/{file}")
    public ResponseEntity<String> addProduct(@PathVariable Category category,
                                             @PathVariable String title,
                                             @PathVariable String description,
                                             @PathVariable int price,
                                             @PathVariable("file") MultipartFile file) throws IOException {
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
        return ResponseEntity.accepted().body("Product was created");
    }
    @PostMapping("/updateProduct/{product}/{category}/{title}/{description}/{price}")
    public ResponseEntity<String> productSave(
            @PathVariable Category category,
            @PathVariable String title,
            @PathVariable String description,
            @PathVariable int price,
            @PathVariable Product product
    ) {
        product.setCategory(category);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        productRepo.save(product);

        return ResponseEntity.accepted().body("Product was updated");
    }

    @ExceptionHandler(TypeMismatchException.class)
    public
    @ResponseBody
    String typeMismatchExpcetionHandler(Exception exception, HttpServletRequest request) {
        return "Wrong category,price or variables is not in url: Category must be (milk,training,ice_cream,chocolate) and price must be number.";
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public
    @ResponseBody
    String missingParameterExceptionHandler(Exception exception, HttpServletRequest request) {
        return "missing param";
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String generalExceptionHandler(Exception exception, HttpServletRequest request) {
        return "No product with this id";
    }
}
