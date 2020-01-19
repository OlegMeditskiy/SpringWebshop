package com.example.sfff.repos;

import com.example.sfff.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct,Long> {
    List<OrderProduct> findByOrderId(Long id);
    void deleteAllByOrderId(Long id);
    void deleteAllByProductId(int id);
}
