package com.example.sfff.repos;

import com.example.sfff.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    List<Product> findByTitleContaining(String title);
}