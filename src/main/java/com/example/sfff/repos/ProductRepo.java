package com.example.sfff.repos;

import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Category;
import com.example.sfff.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByTitleContaining(String title);
    List<Product> findAllByOrderByIdDesc();
    Product findById(Integer id);
    List<Product> findByCategory (Category category);
    void delete(Product p);
}