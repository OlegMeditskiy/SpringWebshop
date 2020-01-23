package com.example.sfff.repos;

import com.example.sfff.domain.Category;
import com.example.sfff.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByTitleContaining(String title);
    List<Product> findByDescriptionContaining(String title);
    List<Product> findAllByOrderByIdDesc();
    Optional<Product> findById(Long id);
    List<Product> findByCategory (Category category);
    void delete(Product p);
}