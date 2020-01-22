package com.example.sfff.repos;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByProductId(int id);
    List<CartProduct> findByCartId(Long id);
    List<CartProduct> findByCartOrderById(Cart cart);
    Optional<CartProduct> findById(Long id);
    void delete(CartProduct cp);
    void deleteById(Long id);
    void deleteAllByCartId(Long id);
    void deleteAllByProductId(int id);
}
