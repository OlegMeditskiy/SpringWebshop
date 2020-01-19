package com.example.sfff.repos;

import com.example.sfff.domain.Order;
import com.example.sfff.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUserId (Long id);
    Optional<Order> findById (Long id);
    void delete (Order order);
}
