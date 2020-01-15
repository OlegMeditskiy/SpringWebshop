package com.example.sfff.repos;

import com.example.sfff.domain.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepo extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByProductId(Integer id);
    Optional<CartProduct> findById(Long id);
    void delete(CartProduct cp);
    void deleteById(Long id);
}
