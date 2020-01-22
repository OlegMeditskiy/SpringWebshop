package com.example.sfff.repos;

import com.example.sfff.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
    Optional<Cart> findById(Long id);
    void delete(Cart cart);
}
